/*Q-learning is a reinforcement learning technique used in machine learning.
The goal of Q-learning is to learn a policy, which tells an agent what action to take under what circumstances.
It does not require a model of the environment and can handle problems with stochastic transitions and rewards, without requiring adaptations*/

/**
 * Simulation of Qlearning Value Iteration for a 2d Puzzle solved by dynamic Programming 
 */
public class QLearning {
   
    public static double[][] game = new double[5][5];
    public static double[][] rewards = new double[5][5];
    
    public static void main(String[] args) {
        
        int goalI = 4;
        int goalJ = 4;
        double alpha = 0.6;
        double discountedFactor=0.2;

        rewards[goalI][goalJ] = 100;
        // Added a blocker or a pothole 
        rewards[3][4]=-200;
        int episodes = 30000;
     
        for (int e = 0; e < episodes; e++) {
            
            int randomI = (int) getRandomIntegerBetweenRange(0, 4);
            int randomJ = (int) getRandomIntegerBetweenRange(0, 4);
            int i = randomI, j = randomJ;
            while (true) {
                MaxValue maxValue = getNextMax(game, i, j);
                
                /// Q learning Value function implemented using dynamic Programming. 
                game[i][j] = (1 - alpha) * game[i][j] + alpha * (rewards[i][j] + discountedFactor * maxValue.maxValue);
                i = maxValue.i;
                j = maxValue.j;
                if (i != goalI && j != goalJ) {
                    break;
                }
            }

        }
        printDp(game);

    }

    public static double getRandomIntegerBetweenRange(double min, double max) {
        double x = (int) (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    static class MaxValue {
        double maxValue = 0.0;
        int i;
        int j;

        public MaxValue() {

        }

        public MaxValue(double maxValue, int i, int j) {
            this.maxValue = maxValue;
            this.i = i;
            this.j = j;
        }
    }

    private static MaxValue getNextMax(double[][] dp, int i, int j) {
        double max = Integer.MIN_VALUE;
        MaxValue ans = new MaxValue();

        if (i + 1 < dp.length) {
            max = Math.max(max, dp[i + 1][j]);
            ans = max == dp[i + 1][j] ? new MaxValue(max, i + 1, j) : ans;
        }
        if (j + 1 < dp.length) {
            max = Math.max(max, dp[i][j + 1]);
            ans = max == dp[i][j + 1] ? new MaxValue(max, i, j + 1) : ans;
        }
        return ans;
    }

    public static void printDp(double dp[][]) {
        for (int i = 0; i < dp[0].length; i++) {
            for (int j = 0; j < dp.length; j++) {
                System.out.print(Math.ceil(dp[i][j]*1000)+"\t");
            }
            System.out.println();
        }

    }
}
