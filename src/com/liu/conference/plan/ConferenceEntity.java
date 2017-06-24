/**
 * 文件名：Conference.java
 * 版权： www.liumingmusic.win
 * 描述：〈描述〉
 * 修改时间：2016年9月18日
 * 修改内容：〈修改内容〉
 */
package com.liu.conference.plan;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉会议实体信息
 * 
 * @author liumingming
 * @version [版本号, 2016年9月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConferenceEntity {
    
    /**
     * 日期时间格式化
     */
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
    
    /**
     * 会议时长
     */
    private int duration;
    
    /**
     * 会议内容
     */
    private String content;
    
    /**
     * 最初的文字内容
     */
    private String originalcontent;
    
    /**
     * 会议开始时间
     */
    private String beginTime;
    
    /**
     * 会议结束时间
     */
    private Calendar endTime;
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉构造函数 方便传值
     * 
     * @param duration 时长
     * @param content 会议内容
     * @param originalcontent 最初的会议文本内容
     * @see [类、类#方法、类#成员]
     */
    public ConferenceEntity(int duration, String content,
        String originalcontent) {
        super();
        this.duration = duration;
        this.content = content;
        this.originalcontent = originalcontent;
    }
    
    public int getDuration() {
        return duration;
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉会议时长
     * 
     * @param duration 会议时长
     * @see [类、类#方法、类#成员]
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉会议时长
     * 
     * @return 会议时长
     * @see [类、类#方法、类#成员]
     */
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getOriginalcontent() {
        return originalcontent;
    }
    
    public void setOriginalcontent(String originalcontent) {
        this.originalcontent = originalcontent;
    }
    
    public String getBeginTime() {
        return sdf.format(beginTime);
    }
    
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    
    public Calendar getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public String toString() {
        return "beginTime: " + beginTime + "        duration: " + duration + "min \t"
            + content;
    }
}
