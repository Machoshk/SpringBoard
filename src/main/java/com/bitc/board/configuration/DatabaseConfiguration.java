package com.bitc.board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	@Bean 
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml")); 
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	};
	
	
}


package com.bitc.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
		
		@Autowired
		private ApplicationContext applicationContext;
		
		
		@Bean
	// 설정 파일 속성을 지정하기 위한 어노테이션
		@ConfigurationProperties(prefix="spring.datasource.hikari")
		public HikariConfig hikariConfig() {
			return new HikariConfig();
		}
		
		@Bean
		// 위에서 설정한 내용을 가지고 실제 데이터 베이스와 연결
		public DataSource dataSource() throws Exception{
			DataSource dataSource = new HikariDataSource(hikariConfig());
			System.out.println(dataSource.toString());
			return dataSource;
		}
		
		/// mybatis 설정 ///

		// -- 객체지향 프로그램은 클래스를 사용하고, 관계형 데이터 베이스는 테이블을 사용하기 때문에 객체 모델과 관계형 데이터베이스 모델간의 불일치가 존재함
		// -- ORM을 통해서 객체와 모델간의 불일치를 해결하는데 사용함
		// -- 객체를 통해서 간접적으로 DB를 컨트롤할 수 있음
		// -- 어떠한 프로그램언어와 데이터 베이스의 종류와 상관없이 사용하는 방식을 모두 통일해서 사용할 수 있음
		@Bean 
		public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
			// mybatis 사용 선언
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//	 		mybatis에서 사용하는 데이터베이스 설정	
			sqlSessionFactoryBean.setDataSource(dataSource);
//			mybatis와 자바클래스를 연동할 xml 파일 위치 설정
//			classpath - /src/main/resources 폴더를 의미
//			/mapper/**/ -resources 폴더 아래의 mapper 폴더 밑에 있는 모든 폴더
//			/sql-*.xml - 이름이 sql- 로 시작하고 확장자가 xml인 모든 파일
			sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml")); //classpath:/mapper/**/sql-*.xml
//			mybatis 추가 설정
			sqlSessionFactoryBean.setConfiguration(mybatisConfig());
			
			return sqlSessionFactoryBean.getObject();
		}
		
		@Bean
		public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
			return new SqlSessionTemplate(sqlSessionFactory);
		}
		
		@Bean
		@ConfigurationProperties(prefix="mybatis.configuration")
//		지정한 설정 파일에서 mybatis 설정 속성을 가져오기
//		mybatis 설정 사용
		public org.apache.ibatis.session.Configuration mybatisConfig(){
			return new org.apache.ibatis.session.Configuration();
		};
		
		
	}

















}














