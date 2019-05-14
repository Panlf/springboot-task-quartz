package com.plf.task.quartz.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

	private boolean flag;
	private String msg;
	private Object data;
	
	public static Result success(){
		return new Result(true,"处理成功",null);
	}
	
	public static Result success(String msg){
		return new Result(true,msg,null);
	}

	public static Result success(Object data){
		return new Result(true,"处理成功",data);
	}
	
	public static Result fail(){
		return new Result(false,"处理失败",null);
	}
	
	public static Result fail(String msg){
		return new Result(false,msg,null);
	}

	public static Result fail(Object data){
		return new Result(false,"处理失败",data);
	}
	
	public static Result result(boolean flag,String msg,Object data){
		return new Result(flag,msg,data);
	}
}
