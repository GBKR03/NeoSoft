package com.neo.bharat.spring.service.impl;

import com.neo.bharat.spring.dto.UserRequestBody;
import com.neo.bharat.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.neo.bharat.spring.repository.UserRepository;
import com.neo.bharat.spring.service.UserService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseEntity<UserRequestBody> addUser(UserRequestBody userRequestBody){
		if(!validateAddUser(userRequestBody)){
			return ResponseEntity.badRequest().body(null);
		}else{
			try{
				User user = convertUserRequestBodyToUser(userRequestBody);
				user = userRepository.save(user);
				return ResponseEntity.ok(convertUserToUserRequestBody(user));
			}catch(ParseException e){
				return ResponseEntity.unprocessableEntity().body(null);
			}
		}
	}
	@Override
	public ResponseEntity<List<UserRequestBody>> searchUser(String firstName, String surname, String pinCode){
		if(!StringUtils.isEmpty(firstName)){
			List<User> userList = userRepository.findAllByFirstName(firstName);
			List<UserRequestBody> userRequestBodyList = convertUserBodiesToUserRequestBodies(userList);
			if(!CollectionUtils.isEmpty(userRequestBodyList)){
				return ResponseEntity.ok(userRequestBodyList);
			}
		}
		if(!StringUtils.isEmpty(surname)){
			List<User> userList = userRepository.findAllBySurName(surname);
			List<UserRequestBody> userRequestBodyList = convertUserBodiesToUserRequestBodies(userList);
			if(!CollectionUtils.isEmpty(userRequestBodyList)){
				return ResponseEntity.ok(userRequestBodyList);
			}
		}
		if(!StringUtils.isEmpty(pinCode)){
			List<User> userList = userRepository.findAllByPincode(pinCode);
			List<UserRequestBody> userRequestBodyList = convertUserBodiesToUserRequestBodies(userList);
			if(!CollectionUtils.isEmpty(userRequestBodyList)){
				return ResponseEntity.ok(userRequestBodyList);
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@Override
	public ResponseEntity<String> deleteUser(int softDelete, String userId){
			Optional<User> userOptional = userRepository.findById(Integer.valueOf(userId));
			if(userOptional.isPresent()){
				User user = userOptional.get();
				if(softDelete == 0){
					user.setIsActive(1);
					userRepository.save(user);
					return ResponseEntity.of(Optional.of("User Soft Delete is Done"));

				}else{
					userRepository.delete(user);
					return ResponseEntity.of(Optional.of("User Hard Delete is Done"));
				}
			}
			return ResponseEntity.of(Optional.of("No Data Found for User ID:"+userId));

	}

	private List<UserRequestBody> convertUserBodiesToUserRequestBodies(List<User> userList){
		if(CollectionUtils.isEmpty(userList)) return null;
		List<UserRequestBody> userRequestBodyList = new ArrayList<>();
		Collections.sort(userList, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.getDob().compareTo(o2.getDob());
			}
		});
		for(User user: userList) {
			userRequestBodyList.add(convertUserToUserRequestBody(user));
		}
		return userRequestBodyList;
	}

	private UserRequestBody convertUserToUserRequestBody(User user){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
		UserRequestBody userRequestBody = new UserRequestBody();
		userRequestBody.setUserId(String.valueOf(user.getUserId()));
		userRequestBody.setAddress(user.getAddress());
		userRequestBody.setDob(simpleDateFormat.format(user.getDob()));
		userRequestBody.setFirstName(user.getFirstName());
		userRequestBody.setJoiningDate(simpleDateFormat.format(user.getJoiningDate()));
		userRequestBody.setMobile(String.valueOf(user.getMobile()));
		userRequestBody.setPincode(user.getPincode());
		userRequestBody.setSurName(user.getSurName());
		return userRequestBody;
	}

	private User convertUserRequestBodyToUser(UserRequestBody userRequestBody) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
		User user = new User();
		user.setAddress(userRequestBody.getAddress());
		user.setDob(simpleDateFormat.parse(userRequestBody.getDob()));
		user.setFirstName(userRequestBody.getFirstName());
		user.setJoiningDate(simpleDateFormat.parse(userRequestBody.getJoiningDate()));
		user.setIsActive(0);
		user.setMobile(userRequestBody.getMobile());
		user.setPincode(userRequestBody.getPincode());
		user.setSurName(userRequestBody.getSurName());
		return user;
	}


	private boolean validateAddUser(UserRequestBody userRequestBody){
		if(StringUtils.isEmpty(userRequestBody.getAddress())
			|| StringUtils.isEmpty(userRequestBody.getDob())
				|| StringUtils.isEmpty(userRequestBody.getUserName())
				|| StringUtils.isEmpty(userRequestBody.getFirstName())
				|| StringUtils.isEmpty(userRequestBody.getJoiningDate())
				|| StringUtils.isEmpty(userRequestBody.getMobile())
				|| StringUtils.isEmpty(userRequestBody.getPincode())
				|| StringUtils.isEmpty(userRequestBody.getSurName())
				|| StringUtils.isEmpty(userRequestBody.getPincode()))
			return false;
		return true;
	}

}
