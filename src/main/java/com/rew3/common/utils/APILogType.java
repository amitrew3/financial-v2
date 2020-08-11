package com.rew3.common.utils;

import com.rew3.common.model.Flags.EntityFlags;

public enum APILogType implements EntityFlags {
	SUCCESS("Success", (byte) 1), WARNING("Warning", (byte) 5), ERROR("Error", (byte) 11), EXCEPTION("Exception", (byte) 12);

	private final String string;
	private final byte flag;

	private APILogType(String string, byte flag) {
		this.string = string;
		this.flag = flag;
	}

	public Byte getFlag() {
		return this.flag;
	}

	public String getString() {
		return this.string;
	}
}
