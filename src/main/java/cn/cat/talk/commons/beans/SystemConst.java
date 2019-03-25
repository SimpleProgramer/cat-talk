package cn.cat.talk.commons.beans;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Auther: qijiang
 * @Date: 2018/9/29 10:04
 * @Description:系统常量
 */

public class SystemConst {

	/******************** ---系统全局变量---- ****************/
	public final static String SystemNameConst = "activity";
	public final static String SystemServiceNameConst = "lefull-activity-service";
	public final static String RedisCityConst = "city";
	public final static String SystemHttp = "https://api-dev.lefull.cn/configcenter";
	public final static String SystemQueueConst = "ahaQueue";
	public final static String SystemVersions = "/api/v1"; // 版本号
	public final static String SystemSpiVersions = "/spi/v1"; // 版本号
	public final static String SystemConfig = "config-center"; // 版本号
	public final static String SystemAppid = "wxa2d6b088eaf34992"; // appid
	public final static Integer SystemSendTime = 180; // 提前几个小时进行通知 目前是三小时 10800
	public final static String SystemRediskey = "lefull-activity-service:"; // redisKey前缀
	public final static String WxMsgModelActive = "Svq1ap0FDZD4lsW0viRQIwF6HycGAUWpLDZXD7Sb5fY"; // 活动签到提醒
	public final static String WxMsgModelCancel = "5J637AxsaO3AzdXXDnfs1JAMOaGs8gqU6wwVhU2ilMA"; // 取消活动提醒
	public final static String ForPage = "/pages/orderRecord/orderRecord"; // 待支付提醒
	public final static String SystemTxExchange = "transferExchange";
	public final static String SystemTransfer = "transferQueue4j";
	public final static String SYSTEMAPI_USER_INFO_HEADER = "SYSTEMAPI_USER_INFO_HEADER";
	public final static String APPLICATION_NAME = "lefull-service-demo-dev";
	public static final String NOTIFY_URL = "/tenancy/spi/v1/tenancyOrder/pay/callback";
    // 系统管理端默认账号密码
	//预定场景CODE
	public final static String SERVICE_CODE_PREORDAIN = "preordain";
	//入住场景CODE
	public final static String SERVICE_CODE_RENT = "rent";
	//续租场景CODE
	public final static String SERVICE_CODE_RENEW = "renew";
	public final static String SERVICE_CODE_SWITCH = "switch_room";
	// 系统管理端默认账号密码
	public static String userName = "root";
	public static String userPass = "root";
	public static String TokenKey = "token_key";
	// 量贩团活动uuid
//   public config-center String MassRetailActiveUUID = "817fd6ef26a241808452e40dc10769e7";
//   //啊哈生活小程序主键uuid
//   public config-center String AhaStyleUUID = "52d3fd1f35a84ae5902b125277124c2a";

	// springboot表单校验多个错误的分割符合
	public final static String VALIDATE_GS = "<BR/>";
//   public config-center void main(String[] args){
//      System.out.println(UUIDSequenceWorker.generateKey());
//   }

	public final static String eToken = "lefull-auth-server:eToken:";
	public final static String uToken = "lefull-auth-server:uToken:";

	public final static String CHECKIN_SCENE_CODE = "rent";
	public final static String RENEWAL_SCENE_CODE = "renew";
	public final static String CHECKIN_RENEWAL_SCENE_CODE = "rent,renew";
	public final static String CHANNEL_CODE = "lefull-tenant-app";

	public final static String SUBJECT_CHECKIN_CODE = "relet";
	public final static String RENEWAL_CHECKIN_CODE = "keepOn";

	public final static Integer SHORTRENT_STATUS_UNUSED = 0;
	public final static Integer SHORTRENT_STATUS_USED = 1;
	public final static Integer SHORTRENT_STATUS_ABOLISH = 2;

	public final static String COUPON_REDIS_KEYS = "coupon:contractId:";

	/******************** ---系统全局变量---- ****************/

	/** 入住场景code **/
	public final static String PREORDAIN_SCENE_CODE = "preordain";

	public final static String renewals = "renewals";
	/**
	 * 包含周期的科目名
	 */
	public final static List<String> subjects = Lists.newArrayList("押金","租金");

	public static String surrender_redis_key = "lefull-tenancy-microservice:surrender:billIds:";
}
