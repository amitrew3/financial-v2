package com.rew3.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rew3.billing.invoice.model.AbstractDTO;
import com.rew3.billing.invoice.model.InvoiceDTO;
import com.rew3.billing.shared.model.AbstractEntity;
import com.rew3.common.interceptor.ACL;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    public static Timestamp convertObjectToTimestamp(Object input) throws ParseException {
        Timestamp timestamp = null;
        if (input != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = (Date) dateFormat.parse((String) input);
            timestamp = new Timestamp(parsedDate.getTime());
        }
        return timestamp;
    }
   /* public static Timestamp convertObjectToTimestamp(Object input) throws ParseException {
        Timestamp timestamp = null;
        if (input != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date parsedDate = (Date) dateFormat.parse((String) input);
            timestamp = new Timestamp(parsedDate.getTime());
        }
        return timestamp;
    }*/

    public static Timestamp convertObjectToTimestampWithTimeZone(Object input) throws ParseException {
        Timestamp timestamp = null;
        if (input != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX");
            // dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsedDate = (Date) dateFormat.parse((String) input);
            timestamp = new Timestamp(parsedDate.getTime());
        }
        return timestamp;
    }

    public static Date convertObjectToDate(Object input) throws ParseException {
        Date parsedDate = null;
        if (input != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = (Date) dateFormat.parse((String) input);

        }
        return parsedDate;
    }

    public static BigInteger convertObjectToBigInteger(Object input) {
        BigInteger number = null;
        if (input != null) {
            String str = input.toString();
            number = new BigInteger(str);
        }
        return number;
    }


    public static Integer convertObjectToInteger(Object input) {
        Integer number = null;
        if (input != null) {
            String str = input.toString();
            number = Integer.parseInt(str);
        }
        return number;
    }

    public static Long convertObjectToLong(Object input) {
        Long number = null;
        if (input != null) {
            String str = input.toString();
            number = Long.parseLong(str);
        }
        return number;
    }

    public static Boolean convertObjectToBoolean(Object input) {
        Boolean val = null;
        if (input != null) {
            String str = input.toString();
            val = Boolean.parseBoolean(str);
        }
        return val;
    }

    public static List<String> convertObjectStringList(Object input) {
        List<String> array = new ArrayList<String>();
        if (input != null) {
            System.out.println("TE");
            System.out.println(input);
            List<Object> inputList = (List<Object>) input;
            System.out.println("D");
            System.out.println(inputList.size());
            System.out.println("------");
            for (Object inputItem : inputList) {
                array.add((String) inputItem);
            }
        }
        return array;
    }

    public static Double convertObjectToDouble(Object input) {
        Double number = null;
        if (input != null) {
            String str = input.toString();
            number = Double.parseDouble(str);
        }
        return number;
    }

    public static Byte convertObjectToByte(Object input) {
        Byte number = null;
        if (input != null) {
            String str = input.toString();
            number = Byte.parseByte(str);
        }
        return number;
    }

    public static List<String> csvToList(Object input) {
        if (input == null) return null;

        String str = (String) input;
        List<String> output = Arrays.asList(str.split(","));
        return output;
    }

    public static String satos(Object input) {
        String str = null;
        String[] strArr = (String[]) input;
        if (strArr.length > 0) {
            str = strArr[0];
        }
        System.out.println(str);
        return str;
    }

    public static List<String> csvToArrayList(String input) {
        List<String> items = Arrays.asList(input.split("\\s*,\\s*"));
        return items;
    }


    public static String getOperator(String operator) {
        if (operator.equals("gt-eq")) {
            return ">=";
        } else if (operator.equals("lt-eq")) {
            return "<=";
        } else if (operator.equals("gt")) {
            return ">";
        } else if (operator.equals("lt")) {
            return "<";
        }else if (operator.equals("eq")) {
            return "=";
        }else if (operator.equals("not-eq")) {
            return "!=";
        } else return "";
    }

    //remove createdAt,deletedAt in json response so as to match actual rew3 response
    public static Object convert(List<Object> o) {
        String s = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        try {
            s = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Object maps = null;
        try {
            maps = mapper.readValue(s, new TypeReference<List<HashMap<String, Object>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maps;
    }

    public static Object convert(Set<Object> o) {
        String s = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            s = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Object maps = null;
        try {
            maps = mapper.readValue(s, new TypeReference<Set<HashMap<String, Object>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maps;
    }

    public static Object convert(Object o) {
        String s = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            s = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Object map = null;
        try {
            map = mapper.readValue(s, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static ACL convertToACL(String s) {

        ACL acl = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            acl = mapper.readValue(s, new TypeReference<ACL>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return acl;
    }

    public static HashMap convertToTransaction(String s) {

        HashMap acl = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            acl = mapper.readValue(s, new TypeReference<HashMap>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return acl;
    }

    public static HashMap convertToJson(String s) {

        HashMap acl = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            acl = mapper.readValue(s, new TypeReference<HashMap>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return acl;
    }

    public static List<String> convertToIdsList(List<Object> objs) {
        return objs.stream().map(c -> (AbstractDTO) c).map(c -> c.get_id().toString()).collect(Collectors.toList());
    }

    public static StringBuilder append(String s) {
        StringBuilder builder = new StringBuilder(s);
        if (s != null) {
            builder.append(" "); //appending the empty string
            builder.append(s);
            builder.append(" "); //appending the empty string
        }

        return builder;
    }


}