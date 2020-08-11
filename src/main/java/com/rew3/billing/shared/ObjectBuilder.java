package com.rew3.billing.shared;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectBuilder {

    public ObjectBuilder() {

    }

    Object obj;
    Class clazz;


    public ObjectBuilder(Object obj, Class clazz) throws ClassNotFoundException {
        this.clazz = clazz;
        this.obj = obj;
    }

    void setGr(Boolean gr) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Method method = clazz.getMethod("setGr", Boolean.class);
        method.invoke(obj, gr);


    }

    void setGw(Boolean gw) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Method method = clazz.getMethod("setGw", Boolean.class);
        method.invoke(obj, gw);


    }


    void setR(String r) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {

        Method getRMethod = clazz.getMethod("getW");
        String csv = (String) getRMethod.invoke(obj);


        if (csv != null && !csv.isEmpty()) {

            csv += ",";

        }
        csv += r;
        Method setRMethod = clazz.getMethod("setR", String.class);
        setRMethod.invoke(obj, csv);



      /*  Field field= clazz.getField("r");
        String rcsv= (String) field.get(obj);
        System.out.println(rcsv);*/


    }

    void setW(String w) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Method getRMethod = clazz.getMethod("getW");
        String csv = (String) getRMethod.invoke(obj);
        if (csv != null && !csv.isEmpty()) {
            csv += ",";
        }
        csv += w;
        Method setRMethod = clazz.getMethod("setW", String.class);
        setRMethod.invoke(obj, csv);

    }


    public Object getObject() {
        return obj;
    }

}
