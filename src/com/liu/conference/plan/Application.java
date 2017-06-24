/**
 * 文件名：Application.java
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
 * 〈功能详细描述〉可运行程序，测试书写会议管理计划是否合理正确
 * 
 * @author liumingming
 * @version [版本号, 2016年9月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Application {
    
    /**
     * 
     *〈一句话功能简述〉
     * 〈功能详细描述〉主程序调用方法
     * @param args
     * @see  [类、类#方法、类#成员]
     */
    public static void main(String[] args) {
        ConferenceGroup group = new ConferenceGroup();
        List<ConferenceEntity>[] lists = group.printConferencePlan(
            Utils.getReativePath() + "/data/test_data.txt");
        System.out.println(
            "---------------------------------------------------------------------------------------- ");
        for (int i = 0; i < lists.length; i++) {
            List<ConferenceEntity> list = lists[i];
            for (ConferenceEntity conferenceEntity : list) {
                System.out.println(conferenceEntity);
            }
            System.out.println(
                "---------------------------------------------------------------------------------------- ");
        }
    }
}
