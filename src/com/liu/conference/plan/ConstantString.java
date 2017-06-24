/**
 * 文件名：ConstantString.java
 * 版权： www.liumingmusic.win
 * 描述：〈描述〉
 * 修改时间：2016年9月18日
 * 修改内容：〈修改内容〉
 */
package com.liu.conference.plan;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉定义程序中使用到的常量
 * 
 * @author liumingming
 * @version [版本号, 2016年9月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConstantString {
    
    /** 早上会议开始时间 */
    public final static int CONFERENCE_BEGIN_TIME = 9;
    
    /** 中午午餐开始时间 */
    public final static int DINNER_TIME = 12;
    
    /** 下午会议开始时间 */
    public final static int AFTERNOON_BEGIN_TIME = 1;
    
    /** 网络会议开始最早时间 */
    public final static int NETWORKING_BEGIN_TIME = 4;
    
    /** 网络会议开始最晚时间 也可是下午最晚时间 */
    public final static int NETWORKING_END_TIME = 5;
    
    /** 网络会议时长时间 */
    public final static int NETWORKING_MINUTE_TIME = 60;
    
    /** 分钟60 */
    public final static int MINUTE = 60;
    
    /** 数字0 */
    public final static int ZERO = 0;
    
    /** 数字1 */
    public final static int ONE = 1;
    
    /** 数字2 */
    public final static int TWO = 2;
    
    /** 数字9 */
    public final static int NINE = 9;
    
    /** 数字12 */
    public final static int TWELVE = 12;
    
    /** 数字5 */
    public final static int FIVE = 5;
    
    /** 全天开会时长 (午餐开始时间 - 上班时间)*60+(下午会议网络最晚开始时间-下午开始会议时间)*60 */
    public final static int WORK_MINUTES =
        (ConstantString.DINNER_TIME - ConstantString.CONFERENCE_BEGIN_TIME)
            * ConstantString.MINUTE
            + (ConstantString.NETWORKING_END_TIME
                - ConstantString.AFTERNOON_BEGIN_TIME) * ConstantString.MINUTE;
    
}
