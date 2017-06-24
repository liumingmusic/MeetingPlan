/**
 * 文件名：ConferenceGroup.java
 * 版权： www.liumingmusic.win
 * 描述：〈描述〉
 * 修改时间：2016年9月18日
 * 修改内容：〈修改内容〉
 */
package com.liu.conference.plan;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉具体的会议管理方法
 * 
 * @author liumingming
 * @version [版本号, 2016年9月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConferenceGroup {
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉返回最后排序和格式化好的数据信息
     * 
     * @param testDataPath 文件路径
     * @return 返回分组和排序好的数据信息
     * @see [类、类#方法、类#成员]
     */
    public List<ConferenceEntity>[] printConferencePlan(String testDataPath) {
        Map<String, List<ConferenceEntity>> map = groupConference(testDataPath);
        List<ConferenceEntity>[] arrayList = new ArrayList[map.size()];
        Set<String> keySet = map.keySet();
        int i = 0;
        for (String key : keySet) {
            arrayList[i] = formatConferenceList(map.get(key));
            i += 1;
        }
        return arrayList;
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉返回分好天数，分好早上和下午会议的Map集合，Map.size > 1 表明会议要进行多天
     * 
     * @param testDataPath 会议行程文件路径
     * @return 返回分好组的Map集合
     * @see [类、类#方法、类#成员]
     */
    private Map<String, List<ConferenceEntity>> groupConference(
        String testDataPath) {
        // 读取数据信息
        ConferenceInfoEntity list = readTxtData(testDataPath);
        // 会议行程安排
        return SortGroup(list);
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉对读取的数据进行分组排序，合理组织成会议时间所需要的时间节点
     * 
     * @param list 待排序分组的数据信息
     * @return 返回分组好的数据
     * @see [类、类#方法、类#成员]
     */
    private Map<String, List<ConferenceEntity>> SortGroup(
        ConferenceInfoEntity list) {
        Map<String, List<ConferenceEntity>> map =
            new HashMap<String, List<ConferenceEntity>>();
        // 临时存储数据 方便后面计算使用
        List<ConferenceEntity> conferenceEntities = list.getList();
        List<ConferenceEntity> tempList = new ArrayList<ConferenceEntity>();
        int sumMinutes = list.getSumMinutes();
        int morning_temporary = 0, afternoon_temporary = 0;
        boolean next_afternoon = false;
        // 输入的会议记录，需要持续的天数
        int conferenceDayNum = (sumMinutes % ConstantString.WORK_MINUTES) == 0
            ? sumMinutes / ConstantString.WORK_MINUTES
            : (sumMinutes / ConstantString.WORK_MINUTES) + ConstantString.ONE;
        // 根据天数循环每天的会议安排
        for (int i = 0; i < conferenceDayNum; i++) {
            // 新集合装计算好的会议安排
            List<ConferenceEntity> entities = new ArrayList<ConferenceEntity>();
            Iterator<ConferenceEntity> iterator = conferenceEntities.iterator();
            // 根据早上会议、下午会议进行循环查找最佳会议记录
            while (iterator.hasNext()) {
                ConferenceEntity conferenceEntity = iterator.next();
                // 早上会议
                if ((morning_temporary
                    + conferenceEntity.getDuration()) <= 180) {
                    morning_temporary += conferenceEntity.getDuration();
                    // 装入集合中
                    entities.add(conferenceEntity);
                    // 移除已经安排好的会议记录
                    iterator.remove();
                    // 判断上午是否已经安排完毕 并且添加一个实体信息为午餐
                    if (morning_temporary == 180) {
                        // 专门设置午餐
                        ConferenceEntity entityLunch = new ConferenceEntity(
                            ConstantString.MINUTE, "Lunch Time", "Lunch Time");
                        entityLunch.setBeginTime("12:00");
                        entities.add(entityLunch);
                        next_afternoon = true;
                    }
                }
                // 下午会议,并且可以进行下午会议安排
                else if (morning_temporary + afternoon_temporary
                    + conferenceEntity.getDuration() <= 420 && next_afternoon) {
                    // 最开始判断临时集合中是否有数据
                    if (tempList.size() > 0) {
                        Iterator<ConferenceEntity> iterator_temp =
                            tempList.iterator();
                        while (iterator_temp.hasNext()) {
                            ConferenceEntity conferenceEntity_temp =
                                iterator_temp.next();
                            afternoon_temporary +=
                                conferenceEntity_temp.getDuration();
                            // 装入集合中
                            entities.add(conferenceEntity_temp);
                            // 移除已经安排好的会议记录
                            iterator_temp.remove();
                        }
                        // 遍历完成之后，清理数据
                        tempList.clear();
                    }
                    afternoon_temporary += conferenceEntity.getDuration();
                    // 装入集合中
                    entities.add(conferenceEntity);
                    // 移除已经安排好的会议记录
                    iterator.remove();
                    // 特殊处理下午的网络会议，
                    if (afternoon_temporary + morning_temporary == 420) {
                        // 单独针对网络会议时间处理
                        ConferenceEntity entityNet =
                            new ConferenceEntity(ConstantString.MINUTE,
                                "Networking Event", "Networking Event");
                        entityNet.setBeginTime("17:00");
                        entities.add(entityNet);
                    }
                }
                // 循环遍历遗留的数据,，而且是在早上会议没有排满的情况下
                else if (!next_afternoon) {
                    tempList.add(conferenceEntity);
                }
            }
            // 重新初始变量值
            morning_temporary = 0;
            afternoon_temporary = 0;
            next_afternoon = false;
            // 把循环的一天添加到map中
            map.put("day " + (i + 1), entities);
        }
        return map;
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉格式化输出排序好的会议行程
     * 
     * @param list 排序好的数据
     * @return 返回数据信息
     * @see [类、类#方法、类#成员]
     */
    private List<ConferenceEntity> formatConferenceList(
        List<ConferenceEntity> list) {
        // 日期时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // 日期类
        Calendar cal = new GregorianCalendar();
        // 保存排序好的数据信息
        List<ConferenceEntity> result = new ArrayList<ConferenceEntity>();
        // 早上会议需要持续的分钟数
        int morning_time =
            (ConstantString.DINNER_TIME - ConstantString.CONFERENCE_BEGIN_TIME)
                * ConstantString.MINUTE;
        // 下午网络会议最晚开始时间
        int afternoon_latest = (ConstantString.NETWORKING_END_TIME
            - ConstantString.AFTERNOON_BEGIN_TIME) * ConstantString.MINUTE
            + morning_time + ConstantString.MINUTE;
        // 早上时间分钟
        int limit = ConstantString.ZERO;
        // 循环遍历时间数据
        for (ConferenceEntity conferenceEntity : list) {
            cal.set(Calendar.HOUR, ConstantString.NINE);
            // 会议开始时间
            if (limit == ConstantString.ZERO) {
                cal.set(Calendar.MINUTE, ConstantString.ZERO);
                cal.set(Calendar.AM_PM, Calendar.AM);
                conferenceEntity.setBeginTime(sdf.format(cal.getTime()));
                result.add(conferenceEntity);
                limit += conferenceEntity.getDuration();
            }
            // 早上会议时间段
            else if (limit < morning_time) {
                cal.set(Calendar.MINUTE, limit);
                cal.set(Calendar.AM_PM, Calendar.AM);
                conferenceEntity.setBeginTime(sdf.format(cal.getTime()));
                result.add(conferenceEntity);
                limit += conferenceEntity.getDuration();
            }
            // 午餐时间
            else if (limit == morning_time) {
                result.add(conferenceEntity);
                limit += ConstantString.MINUTE;
            }
            // 下午会议开始时间
            else if (limit <= afternoon_latest) {
                cal.set(Calendar.MINUTE, limit);
                cal.set(Calendar.AM_PM, Calendar.PM);
                conferenceEntity.setBeginTime(sdf.format(cal.getTime()));
                result.add(conferenceEntity);
                limit += conferenceEntity.getDuration();
            }
            // 下午网络开始时间
            else if (limit == afternoon_latest) {
                result.add(conferenceEntity);
                limit += ConstantString.MINUTE;
            }
        }
        return result;
    }
    
    /**
     * 
     * 〈一句话功能简述〉
     * 〈功能详细描述〉读取数据内容返回集合进行计算
     * 
     * @param testDataPath 数据路径
     * @return 返回读取好的数据信息
     * @see [类、类#方法、类#成员]
     */
    private ConferenceInfoEntity readTxtData(String testDataPath) {
        ConferenceInfoEntity infoEntity = new ConferenceInfoEntity();
        try {
            FileInputStream fstream = new FileInputStream(testDataPath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            List<ConferenceEntity> list = new ArrayList<ConferenceEntity>();
            String strLine, duration, content;
            int time, sumMinutes = 0;
            // 一行一行读取文本信息
            while ((strLine = br.readLine()) != null) {
                duration = strLine.substring(strLine.lastIndexOf(" ") + 1);
                content = strLine.substring(0, strLine.lastIndexOf(" "));
                // 判断是否含有特殊的时间表示
                if ("lightning".equalsIgnoreCase(duration)) {
                    time = ConstantString.NETWORKING_MINUTE_TIME;
                }
                else {
                    // 将读取的时间信息，转换为数字，方便计算
                    time = Integer.parseInt(
                        duration.substring(0, duration.lastIndexOf("min")));
                }
                sumMinutes += time;
                // 添加到实体信息
                ConferenceEntity entity =
                    new ConferenceEntity(time, content, strLine);
                list.add(entity);
            }
            // 最后返回 ConferenceInfoEntity
            infoEntity.setSumMinutes(sumMinutes);
            infoEntity.setList(list);
            in.close();
        }
        catch (Exception e) {
            System.err.println("Error Msg : " + e.getMessage());
        }
        return infoEntity;
    }
}
