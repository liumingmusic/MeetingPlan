/**
 * 文件名：ConferenceInfoEntity.java
 * 版权： www.liumingmusic.win
 * 描述：〈描述〉
 * 修改时间：2016年9月18日
 * 修改内容：〈修改内容〉
 */
package com.liu.conference.plan;

import java.util.List;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉会议信息实体
 * 
 * @author liumingming
 * @version [版本号, 2016年9月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConferenceInfoEntity {
    
    /**
     * 会议总共时长
     */
    private int sumMinutes;
    
    /**
     * 会议集合
     */
    private List<ConferenceEntity> list;
    
    public int getSumMinutes() {
        return sumMinutes;
    }
    
    public void setSumMinutes(int sumMinutes) {
        this.sumMinutes = sumMinutes;
    }
    
    public List<ConferenceEntity> getList() {
        return list;
    }
    
    public void setList(List<ConferenceEntity> list) {
        this.list = list;
    }
    
}
