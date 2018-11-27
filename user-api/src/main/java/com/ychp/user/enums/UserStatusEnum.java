package com.ychp.user.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
public enum UserStatusEnum {
	// 正常
	NORMAL(1),
	// 冻结
	FROZEN(-1);

	UserStatusEnum(int value) {
		this.value = value;
	}

	@Getter
	private int value;

	public static UserStatusEnum fromValue(int value) {
		for (UserStatusEnum item : values()) {
			if(Objects.equals(item.getValue(), value)) {
				return item;
			}
		}
		return null;
	}
}
