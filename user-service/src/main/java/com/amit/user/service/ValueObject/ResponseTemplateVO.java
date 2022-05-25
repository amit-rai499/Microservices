package com.amit.user.service.ValueObject;

import com.amit.user.service.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateVO {

	private User user;
	private Department department;
}
