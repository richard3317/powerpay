package com.icpay.payment.batch.task.chart;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;

@Component("transRptInfoTask")
public class TransRptInfoTask extends BatchTaskTemplate {

	@Override
	protected void doTask() {
		// TODO Auto-generated method stub
		// 1 先获取当前月份，根据月份来查询表格
		Calendar instance = Calendar.getInstance();
		// 需要对月份加1
		int month = instance.get(Calendar.MONDAY) + 1;

		extract(month);

	}

	/**
	 * 进行处理是否选择哪一个表
	 * 
	 * @param transDt
	 *            传入当前月份
	 */
	private void extract(int month) {

		Calendar calendar = Calendar.getInstance();
		//获取当前时间的小时 
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		String trans_dt = df1.format(new Date());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");

		// 七天每个时间段对应的值
		Set<String> hoursSet = new HashSet<>();
		for (int j = 1; j <= 7; j++) {
			//1000*60*60*24   一天   
			//当前时间 减去一天二十四小时   再减去  一个小时    //查询的是前七天      当前时段 前一个小时的数据
			String format = df.format(calendar.getTime().getTime() - (1000 * 60 * 60 * 24) * j - 1000 * 60 * 60);
			hoursSet.add(format);
		}

		// 处理月份的01 02 11 格式
		String monthStr = String.format("%02d", month);
		// 选择拿那张表的数据
		String transLogTbl = "tbl_trans_log" + monthStr;
		
		
		//查询比较时间
        SimpleDateFormat sqlDf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar1 = Calendar.getInstance();
        int currentHour1 = calendar1.get(calendar1.HOUR_OF_DAY)-1;
        String formatDate = sqlDf.format(new Date())+" "+currentHour1;
		
		// 多表联查前一个小时的数据
		String sql = "SELECT a.mchnt_cd,a.mchnt_cn_nm,b.int_trans_cd,sum(b.trans_at) trans_at,b.txn_state,b.trans_chnl, "
				+ " b.rec_crt_ts,b.rec_upd_ts FROM tbl_mchnt_info a INNER JOIN " + transLogTbl
				+ " b ON b.mchnt_cd = a.mchnt_cd"
				+ " WHERE b.rec_crt_ts like '%"+formatDate+"%' GROUP BY b.mchnt_cd,b.int_trans_cd,b.trans_chnl";

		// sql查询，前一个小时所有的数据
		List<Map<String, Object>> queryForList = this.jdbcTemplate.queryForList(sql);
		// System.out.println("多表联查的数据大小为"+queryForList.size());
		if (queryForList.size() == 0) {
			return;
		}		
		// 根据商户号以及交易方式出现tbl_trans_rpt_info表是否存在第一次
		if (queryForList.size() > 0) {
			// 声明set集合，目的是为了去重，查看商户一共有多少
			Set<String> set = new HashSet<>();
			// 声明list集合，目的用来装每个商户id所对应的商户信息，比如成功笔数，失败笔数等等
			List<Map<String, Object>> listValue = new ArrayList<Map<String, Object>>();
			// 第一次遍历，目的是为了区别商户id，查看一共有多少商户
			for (Map<String, Object> map : queryForList) {
				// System.err.println("查询出来的数据为：" + map);
				set.add(map.get("mchnt_cd").toString());
			}
			// 算每个商户所对应的值
			for (String string : set) {
				Map<String, Object> mapList = new HashMap<String, Object>();
				mapList.put("mchnt_cd", string);
				// 成功笔数
				int scceCnt = 0; 
				// 总笔数
				int sum = 0;
				// 第二次遍历，是为了算数据表多出的列便于统计
				
				
				String sucSql="SELECT COUNT(b.mchnt_cd) sucNumber FROM tbl_mchnt_info A INNER JOIN "+transLogTbl+" b ON b.mchnt_cd = A .mchnt_cd WHERE b.rec_crt_ts LIKE '%"+formatDate+"%' AND b.txn_state = 10 AND b.mchnt_cd='"+string+"'";
				
				List<Map<String, Object>> sucSqlList = this.jdbcTemplate.queryForList(sucSql);
					
					if (sucSqlList.size() > 0) {
						scceCnt=Integer.parseInt(sucSqlList.get(0).get("sucNumber").toString());
					}
						
				String sumSql="SELECT COUNT(b.mchnt_cd) sumNumber FROM tbl_mchnt_info A INNER JOIN "+transLogTbl+" b ON b.mchnt_cd = A .mchnt_cd WHERE b.rec_crt_ts LIKE '%"+formatDate+"%' AND b.mchnt_cd='"+string+"'";
				
				List<Map<String, Object>> sumSqlList = this.jdbcTemplate.queryForList(sumSql);
					
					if (sumSqlList.size() > 0) {
						sum=Integer.parseInt(sumSqlList.get(0).get("sumNumber").toString());
					}
				
//				System.err.println("商户号："+string+";总笔数为："+sum+";成功笔数为："+scceCnt+";失败笔数为："+(sum-scceCnt));
				mapList.put("scceCnt", scceCnt);
				mapList.put("failedCnt", sum-scceCnt);
				mapList.put("averageAt", 0);
				mapList.put("averageAtWd", 0);
				mapList.put("sum", sum);
				mapList.put("trans_hour", currentHour1);
				mapList.put("trans_dt", trans_dt);
				listValue.add(mapList);
			}
			// 获得当前系统时间的前一个小时
			for (Map<String, Object> mapList : listValue) {
				for (Map<String, Object> map : queryForList) {
					if (map.get("mchnt_cd").toString().equals(mapList.get("mchnt_cd").toString())) {
						// 存在 则根据条件获得上次交易金额 (last_trans_at) 根据商户号以及交易类型
						String sql1 = "select trans_at  from tbl_trans_rpt_info where mchnt_cd='" + map.get("mchnt_cd")
								+ "'and int_trans_cd='" + map.get("int_trans_cd") + "' order by rec_upd_ts desc";
						// 只会存在一条记录
						List<Map<String, Object>> queryForList2 = this.jdbcTemplate.queryForList(sql1);
						StringBuffer insertSql = new StringBuffer();
						// 查询数据 获取原本数据的上一次交易金额
						double last_trans_at = 0;
						if (queryForList2.size() > 0) {
							last_trans_at = Double.valueOf((queryForList2.get(0).get("trans_at") == null ? "0"
									: queryForList2.get(0).get("trans_at")).toString());
						}
						// 编写 进行添加新表的数据的sql
						// 如果该对象数据库存在，则添加的时候，添加上一次交易金额这个列
						insertSql.append(
								"INSERT INTO `icpay`.`tbl_trans_rpt_info` (`mchnt_cd`,`trans_dt`,`trans_hour`,`mchnt_cn_nm`, `int_trans_cd`, `trans_at`, `last_trans_at`, `txn_state`,`chnl_id`,`average_at`,`average_at_wd`,`scce_cnt`,`failed_cnt`,`all_cnt`) VALUES ('");
						insertSql.append(map.get("mchnt_cd"));
						insertSql.append("','");
						insertSql.append(mapList.get("trans_dt"));
						insertSql.append("','");
						insertSql.append(mapList.get("trans_hour"));
						insertSql.append("','");
						insertSql.append(map.get("mchnt_cn_nm"));
						insertSql.append("','");
						insertSql.append(map.get("int_trans_cd"));
						insertSql.append("',");
						insertSql.append(Double.valueOf(map.get("trans_at").toString()));
						insertSql.append(",");
						insertSql.append(last_trans_at);
						insertSql.append(",'");
						insertSql.append(map.get("txn_state") == null ? "" : map.get("txn_state"));
						insertSql.append("','");
						insertSql.append(map.get("trans_chnl") == null ? "" : map.get("trans_chnl"));
						insertSql.append("','");
						insertSql.append(Double.valueOf(mapList.get("averageAt").toString()));// 平均每小时的交易量
						insertSql.append("','");
						insertSql.append(Double.valueOf(mapList.get("averageAtWd").toString()));// 平均代付每小时的交易量
						insertSql.append("','");
						insertSql.append(Integer.parseInt(mapList.get("scceCnt").toString()));// 成功笔数
						insertSql.append("','");
						insertSql.append(Integer.parseInt(mapList.get("failedCnt").toString()));// 失败笔数
						insertSql.append("','");
						insertSql.append(Integer.parseInt(mapList.get("sum").toString()));// 总笔数
						insertSql.append("')");
						this.jdbcTemplate.execute(insertSql.toString());
					}
				}
			}
			for (String string : set) {
				// 七日平均小时交易量（除代付以外）
				double averageAt = 0;
				// 代付（5210）的平均小时交易量
				double averageAtWd = 0;
				for (String time : hoursSet) {
					// 七日平均每小时的交易量（除代付以外）
					String avgSql = "select sum(trans_at) trans_at from "+transLogTbl+" WHERE rec_crt_ts like '%"
							+ time + "%' and txn_state='10' and int_trans_cd!='5210' and mchnt_cd='" + string + "'";
					List<Map<String, Object>> queryForList2 = this.jdbcTemplate.queryForList(avgSql);
					
					if (queryForList2.size() > 0) {
						if (queryForList2.get(0).get("trans_at") == null) {
							averageAt += 0;
						} else {
							averageAt += Double.valueOf(queryForList2.get(0).get("trans_at").toString());
						}
					}
					//System.err.println("均金额对象："+queryForList2);
					// 代付  平均
					String avgDfSql = "select sum(trans_at) trans_at from "+transLogTbl+" WHERE rec_crt_ts like '%"
							+ time + "%' and txn_state='10' and int_trans_cd='5210' and mchnt_cd='" + string + "'";
					List<Map<String, Object>> avgDfList = this.jdbcTemplate.queryForList(avgDfSql);
						
					//System.out.println("代付平均"+avgDfSql);
					if (avgDfList.size() > 0) {
						if (avgDfList.get(0).get("trans_at") == null) {
							averageAtWd += 0;
						} else {
							averageAtWd += Double.valueOf(avgDfList.get(0).get("trans_at").toString());
						}
					}
				//	System.err.println("代付均金额对象："+avgDfList);
				}
				//System.err.println("商户号为："+string+";均金额："+averageAt+";代付均金额："+averageAtWd);
				// sql修改语句
				String updateSql = "update tbl_trans_rpt_info set average_at=" + (averageAt / 7) + ",average_at_wd="
						+ (averageAtWd / 7) + " where mchnt_cd='" + string
						+ "' and DATE_SUB(CURDATE(), INTERVAL 1 MINUTE) <= date(rec_upd_ts) ";
				this.jdbcTemplate.execute(updateSql.toString());

			}
		}
		// 将集合数据写入磁盘
		try {
			FileWriter fw = new FileWriter("D:\\name.txt");
			for (Map<String, Object> map : queryForList) {
				fw.write(String.valueOf(map));
				fw.write("\r\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String getTaskNm() {
		// TODO Auto-generated method stub
		return "图像显示";
	}
}
