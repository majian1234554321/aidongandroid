package com.leyuan.aidong.entity.campaign;

/**
 * Created by user on 2018/2/23.
 */
public class RankingBean implements Comparable<RankingBean> {


    public String id;//: 爱动号,
    public String name;// "名字",
    public String avatar;// "头像",
    public double score;// 分数,
    public int rank;// 排名

    @Override
    public int compareTo(RankingBean o) {
        return this.rank - o.rank;
    }
}
