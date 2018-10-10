package com.jp.preauth.dao;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jp.preauth.entity.BCUser;

@Repository
public class UserDao {

	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public BCUser getUserById(String email) {
		String sql = "select * from dmh.dbo.bc_user_table where who =?";
		
		
		try {
			@SuppressWarnings("unchecked")
			BCUser user = (BCUser) jdbcTemplate.queryForObject(sql, new Object[] { email }, new BeanPropertyRowMapper(BCUser.class));
			//user.setRole(DMHRoles.USER);
			return user;
		} catch (EmptyResultDataAccessException e) {
			   System.out.println(e);
			   return null;
			}
	}

}
