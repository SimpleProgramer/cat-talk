package cn.cat.talk.commons.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Api请求返回实体
 * 
 * @author created by Singer mail:313402703@qq.com
 * @time 2018/9/29
 * @description
 */
@Data
public class ApiResponse<E> implements Serializable {

	private static final long serialVersionUID = -8099598967725340002L;

	/**
	 * 请求序列号
	 */
	@ApiModelProperty(value = "请求流水号", required = true, notes = "请求流水号", example = "931189104492675072")
	private String requestSeqNo;

	/**
	 * 错误码
	 */
	@ApiModelProperty(value = "响应码", required = true, notes = "响应码", example = "200")
	private String code;

	/**
	 * 错误信息
	 */
	@ApiModelProperty(value = "响应信息", required = true, notes = "响应信息", example = "请求成功")
	private String msg;

	/**
	 * 接口返回时间
	 */
	@ApiModelProperty(value = "接口返回时间", required = true, notes = "接口返回时间", example = "yyyy-MM-dd HH:mm:ss格式，如2017-11-17 00:15:12")
	private String responseTime;

	/**
	 * 接口响应时间
	 */
	@ApiModelProperty(value = "接口响应时间", required = true, notes = "接口响应时间")
	private String spendTime;

	/**
	 * 返回的数据
	 */
	private E data;

	public ApiResponse() {
	}

	public ApiResponse(String requestSeqNo, String errorCode, String msg, E data) {
		this.requestSeqNo = requestSeqNo;
		this.code = errorCode;
		this.msg = msg;
		this.data = data;
	}

}
