package com.ychp.user.api.converter;

import com.ychp.user.api.bean.response.UserVO;
import com.ychp.user.model.User;
import com.ychp.user.model.UserProfile;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
public class UserConverter {

	public static UserVO convertToDetail(User user, UserProfile profile) {
		UserVO userVO = new UserVO();
		fillUserInfo(userVO, user);
		fillUserProfile(userVO, profile);
		return userVO;
	}

	private static void fillUserInfo(UserVO userVO, User user) {
		userVO.setId(user.getId());
		userVO.setName(user.getName());
		userVO.setMobile(user.getMobile());
		userVO.setNickName(user.getNickName());
		userVO.setEmail(user.getEmail());
		userVO.setStatus(user.getStatus());
		userVO.setCreatedAt(user.getCreatedAt());
		userVO.setUpdatedAt(user.getUpdatedAt());
	}

	private static void fillUserProfile(UserVO userVO, UserProfile profile) {
		if(profile == null) {
			return;
		}
		userVO.setAvatar(profile.getAvatar());
		userVO.setGender(profile.getGender());
		userVO.setHomePage(profile.getHomePage());
		userVO.setRealName(profile.getRealName());
		userVO.setBirth(profile.getBirth());
		userVO.setCountryId(profile.getCountryId());
		userVO.setProvinceId(profile.getProvinceId());
		userVO.setCityId(profile.getCityId());
		userVO.setCountry(profile.getCountry());
		userVO.setProvince(profile.getProvince());
		userVO.setCity(profile.getCity());
		userVO.setSynopsis(profile.getSynopsis());
		userVO.setProfile(profile.getProfile());
	}
}
