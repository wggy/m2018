package sw.melody.algorithm.leetcode1.array;

/***
 * Created by ping on 2018/4/13
 * 假设有一个数组，它的第 i 个元素是一个给定的股票在第 i 天的价格。
 * 设计一个算法来找到最大的利润。你可以完成尽可能多的交易（多次买卖股票）。然而，你不能同时参与多个交易（你必须在再次购买前出售股票）。
 */
public class StockMaxProfit {

    public static void main(String[] args) {
        int[] prices = {100, 2000, 300};
        new StockMaxProfit().maxProfit(prices);
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int len = prices.length;
        int sum = 0;
        for (int i=0; i<len-1; i++) {
            if (prices[i+1] > prices[i]) {
                sum += prices[i+1] - prices[i];
            }
        }
        return sum;
    }
}
