package cn.chris.ml.spark.test;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MCWH-1283 on 2018/5/24.
 */
public class Team implements Serializable {

    public static class Builder {
        private List<Player> players;
        private Rank rank;

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Team build() {
            Team team = new Team();
            return team;
        }

        private double getScore() {
            double score = 0;
            if (CollectionUtils.isNotEmpty(players)) {
                for (Player player : players) {
                    if (player != null) {
                        score += player.getScore();
                    }
                }
            }
            if (rank != null) {
                score += rank.getScore();
            }
            return score;
        }
    }
}
