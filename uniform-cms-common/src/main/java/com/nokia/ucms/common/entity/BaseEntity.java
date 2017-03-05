package com.nokia.ucms.common.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
public class BaseEntity implements Serializable 
{
	private static final long serialVersionUID = -2488885189931569213L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public void setId(Integer id) 
	{
		this.id = (id == null || id <= 0) ? null : id;
	}
}
