package io.github.xqplus.leetcode;

import java.util.*;

public class Medium2 {

    /**
     * 799. 香槟塔
     * 我们把玻璃杯摆成金字塔的形状，其中 第一层 有 1 个玻璃杯， 第二层 有 2 个，依次类推到第 100 层，每个玻璃杯将盛有香槟。
     * 从顶层的第一个玻璃杯开始倾倒一些香槟，当顶层的杯子满了，任何溢出的香槟都会立刻等流量的流向左右两侧的玻璃杯。
     * 当左右两边的杯子也满了，就会等流量的流向它们左右两边的杯子，依次类推。（当最底层的玻璃杯满了，香槟会流到地板上）
     * 例如，在倾倒一杯香槟后，最顶层的玻璃杯满了。倾倒了两杯香槟后，第二层的两个玻璃杯各自盛放一半的香槟。
     * 在倒三杯香槟后，第二层的香槟满了 - 此时总共有三个满的玻璃杯。在倒第四杯后，第三层中间的玻璃杯盛放了一半的香槟，
     * 他两边的玻璃杯各自盛放了四分之一的香槟，如下图所示。
     * 现在当倾倒了非负整数杯香槟后，返回第 i 行 j 个玻璃杯所盛放的香槟占玻璃杯容积的比例（ i 和 j 都从0开始）。
     * 示例 1:
     * 输入: poured(倾倒香槟总杯数) = 1, query_glass(杯子的位置数) = 1, query_row(行数) = 1
     * 输出: 0.00000
     * 解释: 我们在顶层（下标是（0，0））倒了一杯香槟后，没有溢出，因此所有在顶层以下的玻璃杯都是空的。
     * 示例 2:
     * 输入: poured(倾倒香槟总杯数) = 2, query_glass(杯子的位置数) = 1, query_row(行数) = 1
     * 输出: 0.50000
     * 解释: 我们在顶层（下标是（0，0）倒了两杯香槟后，有一杯量的香槟将从顶层溢出，位于（1，0）的玻璃杯和（1，1）的玻璃杯平分了这一杯香槟，所以每个玻璃杯有一半的香槟。
     * 示例 3:
     * 输入: poured = 100000009, query_row = 33, query_glass = 17
     * 输出: 1.00000
     * 提示:
     * 0 <= poured <= 10^9
     * 0 <= query_glass <= query_row < 100
     */
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[] row = {poured};
        for (int i = 1; i <= query_row; i++) {
            double[] nextRow = new double[i + 1];
            for (int j = 0; j < i; j++) {
                if (row[j] > 1) {
                    nextRow[j] += (row[j] - 1) / 2;
                    nextRow[j + 1] += (row[j] - 1) / 2;
                }
            }
            row = nextRow;
        }
        return Math.min(1, row[query_glass]);
    }

    /**
     * 2115. 从给定原材料中找到所有可以做出的菜
     * 你有 n 道不同菜的信息。给你一个字符串数组 recipes 和一个二维字符串数组 ingredients 。第 i 道菜的名字为 recipes[i] ，
     * 如果你有它 所有 的原材料 ingredients[i] ，那么你可以 做出 这道菜。一份食谱也可以是 其它 食谱的原料，
     * 也就是说 ingredients[i] 可能包含 recipes 中另一个字符串。
     * 同时给你一个字符串数组 supplies ，它包含你初始时拥有的所有原材料，每一种原材料你都有无限多。
     * 请你返回你可以做出的所有菜。你可以以 任意顺序 返回它们。
     * 注意两道菜在它们的原材料中可能互相包含。
     * 示例 1：
     * 输入：recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
     * 输出：["bread"]
     * 解释：
     * 我们可以做出 "bread" ，因为我们有原材料 "yeast" 和 "flour" 。
     * 示例 2：
     * 输入：recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
     * y->b f->b b->s m->s
     * 输出：["bread","sandwich"]
     * 解释：
     * 我们可以做出 "bread" ，因为我们有原材料 "yeast" 和 "flour" 。
     * 我们可以做出 "sandwich" ，因为我们有原材料 "meat" 且可以做出原材料 "bread" 。
     * 示例 3：
     * 输入：recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
     * 输出：["bread","sandwich","burger"]
     * 解释：
     * 我们可以做出 "bread" ，因为我们有原材料 "yeast" 和 "flour" 。
     * 我们可以做出 "sandwich" ，因为我们有原材料 "meat" 且可以做出原材料 "bread" 。
     * 我们可以做出 "burger" ，因为我们有原材料 "meat" 且可以做出原材料 "bread" 和 "sandwich" 。
     * 示例 4：
     * 输入：recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast"]
     * 输出：[]
     * 解释：
     * 我们没法做出任何菜，因为我们只有原材料 "yeast" 。
     * 提示：
     * n == recipes.length == ingredients.length
     * 1 <= n <= 100
     * 1 <= ingredients[i].length, supplies.length <= 100
     * 1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
     * recipes[i], ingredients[i][j] 和 supplies[k] 只包含小写英文字母。
     * 所有 recipes 和 supplies 中的值互不相同。
     * ingredients[i] 中的字符串互不相同。
     */
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> degree = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            for (String ingredient : ingredients.get(i)) {
                graph.computeIfAbsent(ingredient, k -> new ArrayList<>()).add(recipes[i]);
            }
            degree.put(recipes[i], ingredients.get(i).size());
        }
        List<String> ans = new ArrayList<>();
        Queue<String> queue = new LinkedList<>(Arrays.asList(supplies));
        while (!queue.isEmpty()) {
            String supply = queue.poll();
            if (graph.containsKey(supply)) {
                for (String recipe : graph.get(supply)) {
                    degree.put(recipe, degree.get(recipe) - 1);
                    if (degree.get(recipe) == 0) {
                        queue.offer(recipe);
                        ans.add(recipe);
                    }
                }
            }
        }
        return ans;
    }

    /**
     * 1105. 填充书架
     * 给定一个数组 books ，其中 books[i] = [thicknessi, heighti] 表示第 i 本书的厚度和高度。你也会得到一个整数 shelfWidth 。
     * 按顺序 将这些书摆放到总宽度为 shelfWidth 的书架上。
     * 先选几本书放在书架上（它们的厚度之和小于等于书架的宽度 shelfWidth ），然后再建一层书架。重复这个过程，直到把所有的书都放在书架上。
     * 需要注意的是，在上述过程的每个步骤中，摆放书的顺序与给定图书数组 books 顺序相同。
     * 例如，如果这里有 5 本书，那么可能的一种摆放情况是：第一和第二本书放在第一层书架上，第三本书放在第二层书架上，第四和第五本书放在最后一层书架上。
     * 每一层所摆放的书的最大高度就是这一层书架的层高，书架整体的高度为各层高之和。
     * 以这种方式布置书架，返回书架整体可能的最小高度。
     * 示例 1：
     * 输入：books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelfWidth = 4
     * 输出：6
     * 解释：
     * 3 层书架的高度和为 1 + 3 + 2 = 6 。
     * 第 2 本书不必放在第一层书架上。
     * 示例 2:
     * 输入: books = [[1,3],[2,4],[3,2]], shelfWidth = 6
     * 输出: 4
     * 提示：
     * 1 <= books.length <= 1000
     * 1 <= thicknessi <= shelfWidth <= 1000
     * 1 <= heighti <= 1000
     */
    public int minHeightShelves(int[][] books, int shelfWidth) {
        // dp[i]表示放下第i本书后的最小高度，设上一层能放j本书，则 j...i的总宽度<=shelfWidth,遍历之间的书，取min(dp[j] + max(Height))
        int n = books.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1000_000);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int j = i, sumWidth = 0, maxHeight = 0;
            while (j >= 0 && sumWidth + books[j][0] <= shelfWidth) {
                maxHeight = Math.max(maxHeight, books[j][1]);
                dp[i + 1] = Math.min(dp[i + 1], dp[j] + maxHeight);
                sumWidth += books[j][0];
                j--;
            }
        }
        return dp[n];
    }

    /**
     * 49. 字母异位词分组
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * 示例 1:
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * 解释：
     * 在 strs 中没有字符串可以通过重新排列来形成 "bat"。
     * 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
     * 字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。
     * 示例 2:
     * 输入: strs = [""]
     * 输出: [[""]]
     * 示例 3:
     * 输入: strs = ["a"]
     * 输出: [["a"]]
     * 提示：
     * 1 <= strs.length <= 104
     * 0 <= strs[i].length <= 100
     * strs[i] 仅包含小写字母
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            int[] cnts = new int[26];
            for (char c : str.toCharArray()) {
                cnts[c - 'a']++;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cnts.length; i++) {
                if (cnts[i] > 0) {
                    char c = (char) ('a' + i);
                    for (int j = 0; j < cnts[i]; j++) {
                        sb.append(c);
                    }
                }
            }
            map.computeIfAbsent(sb.toString(), v -> new ArrayList<>()).add(str);
        }
        return new ArrayList<>(map.values());
    }

    /**
     * LCR 060. 前 K 个高频元素
     * 给定一个整数数组 nums 和一个整数 k ，请返回其中出现频率前 k 高的元素。可以按 任意顺序 返回答案。
     * 示例 1：
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * 示例 2：
     * 输入: nums = [1], k = 1
     * 输出: [1]
     * 提示：
     * 1 <= nums.length <= 10^5
     * k 的取值范围是 [1, 数组中不相同的元素的个数]
     * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
     * 进阶：所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
     */
    public static int[] topKFrequent(int[] nums, int k) {
        // 计数排序
        int maxN = Integer.MIN_VALUE, minN = Integer.MAX_VALUE;
        for (int num : nums) {
            maxN = Math.max(maxN, num);
            minN = Math.min(minN, num);
        }
        int[] freq = new int[maxN - minN + 1];
        for (int num : nums) {
            freq[num - minN]++;
        }
        int maxF = Integer.MIN_VALUE, minF = Integer.MAX_VALUE;
        for (int f : freq) {
            maxF = Math.max(maxF, f);
            minF = Math.min(minF, f);
        }
        List<Integer>[] freqNum = new ArrayList[maxF - minF + 1];
        for (int i = 0; i < freq.length; i++) {
            int idx = freq[i] - minF;
            if (freqNum[idx] == null) {
                freqNum[idx] = new ArrayList<>();
            }
            freqNum[idx].add(i + minN);
        }
        int[] ans = new int[k];
        int idx = 0;
        for (int i = maxF - minF; i >= 0; i--) {
            if (freqNum[i] == null) {
                continue;
            }
            for (Integer n : freqNum[i]) {
                ans[idx++] = n;
                if (idx == k) {
                    break;
                }
            }
            if (idx == k) {
                break;
            }
        }
        return ans;

        // 小顶堆
//        Map<Integer, Integer> cntMap = new HashMap<>();
//        for (int num : nums) {
//            cntMap.put(num, cntMap.getOrDefault(num, 0) + 1);
//        }
//        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e[1]));
//        for (Map.Entry<Integer, Integer> entry : cntMap.entrySet()) {
//            if (pq.size() < k) {
//                pq.offer(new int[]{entry.getKey(), entry.getValue()});
//                continue;
//            }
//            if (pq.peek()[1] < entry.getValue()) {
//                pq.poll();
//                pq.offer(new int[]{entry.getKey(), entry.getValue()});
//            }
//        }
//        int[] ans = new int[k];
//        int i = 0;
//        while (!pq.isEmpty()) {
//            ans[i++] = pq.poll()[0];
//        }
//        return ans;
    }

    /**
     * 3107. 使数组中位数等于 K 的最少操作数
     * 给你一个整数数组 nums 和一个 非负 整数 k 。一次操作中，你可以选择任一元素 加 1 或者减 1 。
     * 请你返回将 nums 中位数 变为 k 所需要的 最少 操作次数。
     * 一个数组的中位数指的是数组按非递减顺序排序后最中间的元素。如果数组长度为偶数，我们选择中间两个数的较大值为中位数。
     * 示例 1：
     * 输入：nums = [2,5,6,8,5], k = 4   2 5 6 10 23
     * 输出：2
     * 解释：我们将 nums[1] 和 nums[4] 减 1 得到 [2, 4, 6, 8, 4] 。现在数组的中位数等于 k 。
     * 示例 2：
     * 输入：nums = [2,5,6,8,5], k = 7
     * 输出：3
     * 解释：我们将 nums[1] 增加 1 两次，并且将 nums[2] 增加 1 一次，得到 [2, 7, 7, 8, 5] 。
     * 示例 3：
     * 输入：nums = [1,2,3,4,5,6], k = 4
     * 输出：0
     * 解释：数组中位数已经等于 k 了。
     * 提示：
     * 1 <= nums.length <= 2 * 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= 10^9
     */
    public long minOperationsToMakeMedianK(int[] nums, int k) {
        Arrays.sort(nums);
        int mid = nums.length >> 1;
        long ans = 0;
        if (nums[mid] > k) {
            for (int i = mid; i >= 0 && nums[i] > k; i--) {
                ans += nums[i] - k;
            }
        } else if (nums[mid] < k) {
            for (int i = mid; i < nums.length && nums[i] < k; i++) {
                ans += k - nums[i];
            }
        }
        return ans;
    }

    /**
     * 2261. 含最多 K 个可整除元素的子数组
     * 给你一个整数数组 nums 和两个整数 k 和 p ，找出并返回满足要求的不同的子数组数，要求子数组中最多 k 个可被 p 整除的元素。
     * 如果满足下述条件之一，则认为数组 nums1 和 nums2 是 不同 数组：
     * 两数组长度 不同 ，或者
     * 存在 至少 一个下标 i 满足 nums1[i] != nums2[i] 。
     * 子数组 定义为：数组中的连续元素组成的一个 非空 序列。
     * 示例 1：
     * 输入：nums = [2,3,3,2,2], k = 2, p = 2
     * 输出：11
     * 解释：
     * 位于下标 0、3 和 4 的元素都可以被 p = 2 整除。
     * 共计 11 个不同子数组都满足最多含 k = 2 个可以被 2 整除的元素：
     * [2]、[2,3]、[2,3,3]、[2,3,3,2]、[3]、[3,3]、[3,3,2]、[3,3,2,2]、[3,2]、[3,2,2] 和 [2,2] 。
     * 注意，尽管子数组 [2] 和 [3] 在 nums 中出现不止一次，但统计时只计数一次。
     * 子数组 [2,3,3,2,2] 不满足条件，因为其中有 3 个元素可以被 2 整除。
     * 示例 2：
     * 输入：nums = [1,2,3,4], k = 4, p = 1
     * 输出：10
     * 解释：
     * nums 中的所有元素都可以被 p = 1 整除。
     * 此外，nums 中的每个子数组都满足最多 4 个元素可以被 1 整除。
     * 因为所有子数组互不相同，因此满足所有限制条件的子数组总数为 10 。
     * 提示：
     * 1 <= nums.length <= 200
     * 1 <= nums[i], p <= 200
     * 1 <= k <= nums.length
     * 进阶：
     * 你可以设计并实现时间复杂度为 O(n^2) 的算法解决此问题吗？
     */
    public static int countDistinct(int[] nums, int k, int p) {
        // 遍历的同时统计能被p整除的个数，还要讨论是否重复子数组
        int n = nums.length;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < n; j++) {
                if (nums[j] % p == 0 && ++cnt > k) {
                    break;
                }
                set.add(sb.append(nums[j]).append('#').toString());
            }
        }
        return set.size();
    }

    /**
     * 200. 岛屿数量
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     * 示例 1：
     * 输入：grid = [
     *   ["1","1","1","1","0"],
     *   ["1","1","0","1","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","0","0","0"]
     * ]
     * 输出：1
     * 示例 2：
     * 输入：grid = [
     *   ["1","1","0","0","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","1","0","0"],
     *   ["0","0","0","1","1"]
     * ]
     * 输出：3
     *
     *
     * 提示：
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] 的值为 '0' 或 '1'
     */
    private static final int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 二维平面 上右下左

    private static int m;
    private static int n;
    public int numIslands(char[][] grid) {
        // bfs 遍历矩阵，遇到1且 !visited[i][j]的点 ans+1，然后从这点bfs,维护visited（用修改grid[i][j]来替代visited）
//        int ans = 0, m = grid.length, n = grid[0].length;
//        Queue<int[]> queue = new LinkedList<>();
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if (grid[i][j] == '1') {
//                    ans++;
//                    queue.add(new int[]{i, j});
//                    while (!queue.isEmpty()) {
//                        int[] d = queue.poll();
//                        grid[d[0]][d[1]] = '0';
//                        for (int[] dir : DIRS) {
//                            int x = d[0] + dir[0], y = d[1] + dir[1];
//                            if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == '1') {
//                                queue.add(new int[]{x, y});
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return ans;

        // dfs
        m = grid.length;
        n = grid[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    private void dfs(char[][] grid, int x, int y) {
        if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == '0') {
            return;
        }
        grid[x][y] = '0';
        dfs(grid, x - 1, y);
        dfs(grid, x, y + 1);
        dfs(grid, x + 1, y);
        dfs(grid, x, y - 1);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {14,9,14,1,1,18,5,11};
        System.out.println(countDistinct(nums, 8, 4));
    }
}
