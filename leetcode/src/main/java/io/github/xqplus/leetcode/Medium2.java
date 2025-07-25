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
     * ["1","1","1","1","0"],
     * ["1","1","0","1","0"],
     * ["1","1","0","0","0"],
     * ["0","0","0","0","0"]
     * ]
     * 输出：1
     * 示例 2：
     * 输入：grid = [
     * ["1","1","0","0","0"],
     * ["1","1","0","0","0"],
     * ["0","0","1","0","0"],
     * ["0","0","0","1","1"]
     * ]
     * 输出：3
     * <p>
     * <p>
     * 提示：
     * <p>
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
     * 3282. 到达数组末尾的最大得分
     * 给你一个长度为 n 的整数数组 nums 。
     * 你的目标是从下标 0 出发，到达下标 n - 1 处。每次你只能移动到 更大 的下标处。
     * 从下标 i 跳到下标 j 的得分为 (j - i) * nums[i] 。
     * 请你返回你到达最后一个下标处能得到的 最大总得分 。
     * 示例 1：
     * 输入：nums = [1,3,1,5]
     * 输出：7
     * 解释：
     * 一开始跳到下标 1 处，然后跳到最后一个下标处。总得分为 1 * 1 + 2 * 3 = 7 。
     * 示例 2：
     * 输入：nums = [4,3,1,3,2]
     * 输出：16
     * 解释：
     * 直接跳到最后一个下标处。总得分为 4 * 4 = 16 。
     * 提示：
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     */
    public long findMaximumScore(List<Integer> nums) {
        long ans = 0;
        int n = nums.size(), i = 0, j = 1;
        while (j < n) {
            while (j < n && nums.get(j) <= nums.get(i)) {
                j++;
            }
            ans += (long) nums.get(i) * (j == n ? j - i - 1 : j - i);
            i = j;
            j = i + 1;
        }
        return ans;
    }

    /**
     * 3175. 找到连续赢 K 场比赛的第一位玩家
     * 有 n 位玩家在进行比赛，玩家编号依次为 0 到 n - 1 。
     * 给你一个长度为 n 的整数数组 skills 和一个 正 整数 k ，其中 skills[i] 是第 i 位玩家的技能等级。skills 中所有整数 互不相同 。
     * 所有玩家从编号 0 到 n - 1 排成一列。
     * 比赛进行方式如下：
     * 队列中最前面两名玩家进行一场比赛，技能等级 更高 的玩家胜出。
     * 比赛后，获胜者保持在队列的开头，而失败者排到队列的末尾。
     * 这个比赛的赢家是 第一位连续 赢下 k 场比赛的玩家。
     * 请你返回这个比赛的赢家编号。
     * 示例 1：
     * 输入：skills = [4,2,6,3,9], k = 2
     * 输出：2
     * 解释：
     * 一开始，队列里的玩家为 [0,1,2,3,4] 。比赛过程如下：
     * 玩家 0 和 1 进行一场比赛，玩家 0 的技能等级高于玩家 1 ，玩家 0 胜出，队列变为 [0,2,3,4,1] 。
     * 玩家 0 和 2 进行一场比赛，玩家 2 的技能等级高于玩家 0 ，玩家 2 胜出，队列变为 [2,3,4,1,0] 。
     * 玩家 2 和 3 进行一场比赛，玩家 2 的技能等级高于玩家 3 ，玩家 2 胜出，队列变为 [2,4,1,0,3] 。
     * 玩家 2 连续赢了 k = 2 场比赛，所以赢家是玩家 2 。
     * 示例 2：
     * 输入：skills = [2,5,4], k = 3
     * 输出：1
     * 解释：
     * 一开始，队列里的玩家为 [0,1,2] 。比赛过程如下：
     * 玩家 0 和 1 进行一场比赛，玩家 1 的技能等级高于玩家 0 ，玩家 1 胜出，队列变为 [1,2,0] 。
     * 玩家 1 和 2 进行一场比赛，玩家 1 的技能等级高于玩家 2 ，玩家 1 胜出，队列变为 [1,0,2] 。
     * 玩家 1 和 0 进行一场比赛，玩家 1 的技能等级高于玩家 0 ，玩家 1 胜出，队列变为 [1,2,0] 。
     * 玩家 1 连续赢了 k = 3 场比赛，所以赢家是玩家 1 。
     * 提示：
     * n == skills.length
     * 2 <= n <= 10^5
     * 1 <= k <= 10^9
     * 1 <= skills[i] <= 10^6
     * skills 中的整数互不相同。
     */
    public int findWinningPlayer(int[] skills, int k) {
        int n = skills.length;
        if (k >= n) {
            int max = 0;
            for (int i = 0; i < n; i++) {
                if (skills[i] > skills[max]) {
                    max = i;
                }
            }
            return max;
        }
        int i = 0, cnt = 0;
        for (int j = 1; j < n; j++) {
            if (skills[i] < skills[j]) {
                cnt = 0;
                i = j;
            }
            if (++cnt == k) break;
        }
        return i;
    }

    /**
     * 946. 验证栈序列
     * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
     * 示例 1：
     * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
     * 输出：true
     * 解释：我们可以按以下顺序执行：
     * push(1), push(2), push(3), push(4), pop() -> 4,
     * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
     * 示例 2：
     * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
     * 输出：false
     * 解释：1 不能在 2 之前弹出。
     * 提示：
     * 1 <= pushed.length <= 1000
     * 0 <= pushed[i] <= 1000
     * pushed 的所有元素 互不相同
     * popped.length == pushed.length
     * popped 是 pushed 的一个排列
     */
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stack = new ArrayDeque<>();
        int j = 0;
        for (int k : pushed) {
            stack.push(k);
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 2457. 美丽整数的最小增量
     * 给你两个正整数 n 和 target 。
     * 如果某个整数每一位上的数字相加小于或等于 target ，则认为这个整数是一个 美丽整数 。
     * 找出并返回满足 n + x 是 美丽整数 的最小非负整数 x 。生成的输入保证总可以使 n 变成一个美丽整数。
     * 示例 1：
     * 输入：n = 16, target = 6
     * 输出：4
     * 解释：最初，n 是 16 ，且其每一位数字的和是 1 + 6 = 7 。在加 4 之后，n 变为 20 且每一位数字的和变成 2 + 0 = 2 。可以证明无法加上一个小于 4 的非负整数使 n 变成一个美丽整数。
     * 示例 2：
     * 输入：n = 467, target = 6
     * 输出：33
     * 解释：最初，n 是 467 ，且其每一位数字的和是 4 + 6 + 7 = 17 。在加 33 之后，n 变为 500 且每一位数字的和变成 5 + 0 + 0 = 5 。可以证明无法加上一个小于 33 的非负整数使 n 变成一个美丽整数。
     * 示例 3：
     * 输入：n = 1, target = 1
     * 输出：0
     * 解释：最初，n 是 1 ，且其每一位数字的和是 1 ，已经小于等于 target 。
     * 提示：
     * 1 <= n <= 10^12
     * 1 <= target <= 150
     * 生成的输入保证总可以使 n 变成一个美丽整数。
     */
    public static long makeIntegerBeautiful(long n, int target) {
        List<Integer> bitNums = new ArrayList<>();
        int total = 0;
        long m = n;
        while (m > 0) {
            int bitNum = (int) (m % 10);
            total += bitNum;
            bitNums.add(bitNum);
            m /= 10;
        }
        if (total <= target) {
            return 0;
        }
        int size = bitNums.size();
        long nn = 0, pow = 1;
        for (int i = 0; i < size; i++) {
            if (total > target) {
                if (i == bitNums.size() - 1) {
                    nn += pow * 10;
                } else {
                    total -= bitNums.get(i) - 1;
                    bitNums.set(i + 1, bitNums.get(i + 1) + 1);
                }
            } else {
                nn += bitNums.get(i) * pow;
            }
            pow *= 10;
        }
        return nn - n;
    }

    /**
     * 777. 在 LR 字符串中交换相邻字符
     * 在一个由 'L' , 'R' 和 'X' 三个字符组成的字符串（例如"RXXLRXRXL"）中进行移动操作。一次移动操作指用一个 "LX" 替换一个 "XL"，
     * 或者用一个 "XR" 替换一个 "RX"。现给定起始字符串 start 和结束字符串 result，请编写代码，当且仅当存在一系列移动操作使得 start 可以转换成 result 时， 返回 True。
     * 示例 1：
     * 输入：start = "RXXLRXRXL", result = "XRLXXRRLX"
     * 输出：true
     * 解释：通过以下步骤我们可以将 start 转化为 result：
     * RXXLRXRXL ->
     * XRXLRXRXL ->
     * XRLXRXRXL ->
     * XRLXXRRXL ->
     * XRLXXRRLX
     * 示例 2：
     * 输入：start = "X", result = "L"
     * 输出：false
     * 提示：
     * 1 <= start.length <= 10^4
     * start.length == result.length
     * start 和 result 都只包含 'L', 'R' 或 'X'。
     */
    public boolean canTransform(String start, String result) {
        // 思路分析： 题目的意思是说 ‘R’只能向右移动，并且只能移向’X’，‘L’只能向左移动，并且只能移向’X’。
        // 第一：如果将start、end中的‘X’全部去掉得到的newStart 和 newEnd相等才有可能转换成功。
        // 第二：如果start中'R'的左边'X'的个数超过在end中对应位置的'R'的左边'X'的个数，则不能转换成功，因为start中的'R'只能向右移动，右边的'X'只能增加不能减少
        // 第三：如果end中'L'的左边'X'的个数超过在start中对应位置的'L'的左边'X'的个数，则不能转换成功，因为start中的'L'只能向左移动，左边的'X'只能减少不能增加
        // 双指针
        int ns = start.length(), nr = result.length();
        int ps = 0, pr = 0, cxs = 0, cxr = 0;
        while (ps < ns && pr < nr) {
            while (ps < ns && start.charAt(ps) == 'X') {
                cxs++;
                ps++;
            }
            while (pr < nr && result.charAt(pr) == 'X') {
                cxr++;
                pr++;
            }
            if (ps < ns && pr < nr) {
                char cs = start.charAt(ps), cr = result.charAt(pr);
                if (cs != cr) {
                    return false;
                }
                if ((cs == 'R' && cxs > cxr) || (cs == 'L' && cxr > cxs)) {
                    return false;
                }
                ps++;
                pr++;
            }
        }
        while (ps < ns && start.charAt(ps) == 'X') {
            ps++;
        }
        while (pr < nr && result.charAt(pr) == 'X') {
            pr++;
        }
        return ps == pr;
    }

    /**
     * 2381. 字母移位 II
     * 给你一个小写英文字母组成的字符串 s 和一个二维整数数组 shifts ，其中 shifts[i] = [starti, endi, directioni] 。
     * 对于每个 i ，将 s 中从下标 starti 到下标 endi （两者都包含）所有字符都进行移位运算，如果 directioni = 1 将字符向后移位，
     * 如果 directioni = 0 将字符向前移位。
     * 将一个字符 向后 移位的意思是将这个字符用字母表中 下一个 字母替换（字母表视为环绕的，所以 'z' 变成 'a'）。
     * 类似的，将一个字符 向前 移位的意思是将这个字符用字母表中 前一个 字母替换（字母表是环绕的，所以 'a' 变成 'z' ）。
     * 请你返回对 s 进行所有移位操作以后得到的最终字符串。
     * 示例 1：
     * 输入：s = "abc", shifts = [[0,1,0],[1,2,1],[0,2,1]]
     * 输出："ace"
     * 解释：首先，将下标从 0 到 1 的字母向前移位，得到 s = "zac" 。
     * 然后，将下标从 1 到 2 的字母向后移位，得到 s = "zbd" 。
     * 最后，将下标从 0 到 2 的字符向后移位，得到 s = "ace" 。
     * 示例 2:
     * 输入：s = "dztz", shifts = [[0,0,0],[1,1,1]]
     * 输出："catz"
     * 解释：首先，将下标从 0 到 0 的字母向前移位，得到 s = "cztz" 。
     * 最后，将下标从 1 到 1 的字符向后移位，得到 s = "catz" 。
     * 提示：
     * 1 <= s.length, shifts.length <= 5 * 10^4
     * shifts[i].length == 3
     * 0 <= starti <= endi < s.length
     * 0 <= directioni <= 1
     * s 只包含小写英文字母。
     */
    public static String shiftingLetters(String s, int[][] shifts) {
        // 差分
        Map<Integer, Integer> diffMap = new HashMap<>();
        for (int[] shift : shifts) {
            int d = shift[2] == 1 ? 1 : -1;
            diffMap.put(shift[0], diffMap.getOrDefault(shift[0], 0) + d);
            diffMap.put(shift[1] + 1, diffMap.getOrDefault(shift[1] + 1, 0) - d);
        }
        char[] chars = s.toCharArray();
        int preSum = 0;
        for (int i = 0; i < chars.length; i++) {
            preSum = (preSum + diffMap.getOrDefault(i, 0)) % 26 + 26;
            chars[i] = (char) ('a' + (chars[i] - 'a' + preSum) % 26);
        }
        return new String(chars);
    }

    /**
     * 1881. 插入后的最大值
     * 给你一个非常大的整数 n 和一个整数数字 x ，大整数 n 用一个字符串表示。n 中每一位数字和数字 x 都处于闭区间 [1, 9] 中，且 n 可能表示一个 负数 。
     * 你打算通过在 n 的十进制表示的任意位置插入 x 来 最大化 n 的 数值。但 不能 在负号的左边插入 x 。
     * 例如，如果 n = 73 且 x = 6 ，那么最佳方案是将 6 插入 7 和 3 之间，使 n = 763 。
     * 如果 n = -55 且 x = 2 ，那么最佳方案是将 2 插在第一个 5 之前，使 n = -255 。
     * 返回插入操作后，用字符串表示的 n 的最大值。
     * 示例 1：
     * 输入：n = "99", x = 9
     * 输出："999"
     * 解释：不管在哪里插入 9 ，结果都是相同的。
     * 示例 2：
     * 输入：n = "-13", x = 2
     * 输出："-123"
     * 解释：向 n 中插入 x 可以得到 -213、-123 或者 -132 ，三者中最大的是 -123 。
     * 提示：
     * 1 <= n.length <= 10^5
     * 1 <= x <= 9
     * n 中每一位的数字都在闭区间 [1, 9] 中。
     * n 代表一个有效的整数。
     * 当 n 表示负数时，将会以字符 '-' 开始。
     */
    public String maxValue(String n, int x) {
        int len = n.length(), index = -1;
        if (n.charAt(0) == '-') {
            for (int i = 1; i < len; i++) {
                if (n.charAt(i) - '0' > x) {
                    index = i;
                    break;
                }
            }
        } else {
            for (int i = 1; i < len; i++) {
                if (n.charAt(i) - '0' < x) {
                    index = i;
                    break;
                }
            }
        }
        return index == -1 ? n + x : n.substring(0, index) + x + n.substring(index);
    }

    /**
     * 720. 词典中最长的单词
     * 给出一个字符串数组 words 组成的一本英语词典。返回能够通过 words 中其它单词逐步添加一个字母来构造得到的 words 中最长的单词。
     * 若其中有多个可行的答案，则返回答案中字典序最小的单词。若无答案，则返回空字符串。
     * 请注意，单词应该从左到右构建，每个额外的字符都添加到前一个单词的结尾。
     * 示例 1：
     * 输入：words = ["w","wo","wor","worl", "world"]
     * 输出："world"
     * 解释： 单词"world"可由"w", "wo", "wor", 和 "worl"逐步添加一个字母组成。
     * 示例 2：
     * 输入：words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
     * 输出："apple"
     * 解释："apply" 和 "apple" 都能由词典中的单词组成。但是 "apple" 的字典序小于 "apply"
     * 提示：
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 30
     * 所有输入的字符串 words[i] 都只包含小写字母。
     */
    public String longestWord(String[] words) {
        Set<String> set = new HashSet<>(Arrays.asList(words));
        String ans = "";
        for (String word : words) {
            boolean f = true;
            int n = word.length() - 1;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(word.charAt(i));
                if (!set.contains(sb.toString())) {
                    f = false;
                    break;
                }
            }
            if (f) {
                if (word.length() > ans.length()) {
                    ans = word;
                } else if (word.length() == ans.length() && word.compareTo(ans) < 0) {
                    ans = word;
                }
            }
        }
        return ans;
    }

    /**
     * 1401. 圆和矩形是否有重叠
     * 给你一个以 (radius, xCenter, yCenter) 表示的圆和一个与坐标轴平行的矩形 (x1, y1, x2, y2) ，其中 (x1, y1) 是矩形左下角的坐标，而 (x2, y2) 是右上角的坐标。
     * 如果圆和矩形有重叠的部分，请你返回 true ，否则返回 false 。
     * 换句话说，请你检测是否 存在 点 (xi, yi) ，它既在圆上也在矩形上（两者都包括点落在边界上的情况）。
     * 示例 1 ：
     * 输入：radius = 1, xCenter = 0, yCenter = 0, x1 = 1, y1 = -1, x2 = 3, y2 = 1
     * 输出：true
     * 解释：圆和矩形存在公共点 (1,0) 。
     * 示例 2 ：
     * 输入：radius = 1, xCenter = 1, yCenter = 1, x1 = 1, y1 = -3, x2 = 2, y2 = -1
     * 输出：false
     * 示例 3 ：
     * 输入：radius = 1, xCenter = 0, yCenter = 0, x1 = -1, y1 = 0, x2 = 0, y2 = 1
     * 输出：true
     * 提示：
     * 1 <= radius <= 2000
     * -10^4 <= xCenter, yCenter <= 10^4
     * -10^4 <= x1 < x2 <= 10^4
     * -10^4 <= y1 < y2 <= 10^4
     */
    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        int x = Math.max(x1, Math.min(x2, xCenter));
        int y = Math.max(y1, Math.min(y2, yCenter));
        int dis = (x - xCenter) * (x - xCenter) + (y - yCenter) * (y - yCenter);
        return dis <= radius * radius;
    }

    /**
     * 1946. 子字符串突变后可能得到的最大整数
     * 给你一个字符串 num ，该字符串表示一个大整数。另给你一个长度为 10 且 下标从 0  开始 的整数数组 change ，
     * 该数组将 0-9 中的每个数字映射到另一个数字。更规范的说法是，数字 d 映射为数字 change[d] 。
     * 你可以选择 突变  num 的任一子字符串。突变 子字符串意味着将每位数字 num[i] 替换为该数字在 change 中的映射（也就是说，将 num[i] 替换为 change[num[i]]）。
     * 请你找出在对 num 的任一子字符串执行突变操作（也可以不执行）后，可能得到的 最大整数 ，并用字符串表示返回。
     * 子字符串 是字符串中的一个连续序列。
     * 示例 1：
     * 输入：num = "132", change = [9,8,5,0,3,6,4,2,6,8]
     * 输出："832"
     * 解释：替换子字符串 "1"：
     * - 1 映射为 change[1] = 8 。
     * 因此 "132" 变为 "832" 。
     * "832" 是可以构造的最大整数，所以返回它的字符串表示。
     * 示例 2：
     * 输入：num = "021", change = [9,4,3,5,7,2,1,9,0,6]
     * 输出："934"
     * 解释：替换子字符串 "021"：
     * - 0 映射为 change[0] = 9 。
     * - 2 映射为 change[2] = 3 。
     * - 1 映射为 change[1] = 4 。
     * 因此，"021" 变为 "934" 。
     * "934" 是可以构造的最大整数，所以返回它的字符串表示。
     * 示例 3：
     * 输入：num = "5", change = [1,4,7,5,3,2,5,6,9,4]
     * 输出："5"
     * 解释："5" 已经是可以构造的最大整数，所以返回它的字符串表示。
     * 提示：
     * 1 <= num.length <= 10^5
     * num 仅由数字 0-9 组成
     * change.length == 10
     * 0 <= change[d] <= 9
     */
    public String maximumNumber(String num, int[] change) {
        char[] chars = num.toCharArray();
        boolean startChange = false;
        for (int i = 0; i < chars.length; i++) {
            int digit = chars[i] - '0';
            if (change[digit] <= digit) {
                if (startChange && change[digit] < digit) {
                    break;
                }
            } else {
                startChange = true;
                chars[i] = (char) ('0' + change[digit]);
            }
        }
        return new String(chars);
    }

    /**
     * 3218. 切蛋糕的最小总开销 I
     * 有一个 m x n 大小的矩形蛋糕，需要切成 1 x 1 的小块。
     * 给你整数 m ，n 和两个数组：
     * horizontalCut 的大小为 m - 1 ，其中 horizontalCut[i] 表示沿着水平线 i 切蛋糕的开销。
     * verticalCut 的大小为 n - 1 ，其中 verticalCut[j] 表示沿着垂直线 j 切蛋糕的开销。
     * 一次操作中，你可以选择任意不是 1 x 1 大小的矩形蛋糕并执行以下操作之一：
     * 沿着水平线 i 切开蛋糕，开销为 horizontalCut[i] 。
     * 沿着垂直线 j 切开蛋糕，开销为 verticalCut[j] 。
     * 每次操作后，这块蛋糕都被切成两个独立的小蛋糕。
     * 每次操作的开销都为最开始对应切割线的开销，并且不会改变。
     * 请你返回将蛋糕全部切成 1 x 1 的蛋糕块的 最小 总开销。
     * 示例 1：
     * 输入：m = 3, n = 2, horizontalCut = [1,3], verticalCut = [5]
     * 输出：13
     * 解释：
     * 沿着垂直线 0 切开蛋糕，开销为 5 。
     * 沿着水平线 0 切开 3 x 1 的蛋糕块，开销为 1 。
     * 沿着水平线 0 切开 3 x 1 的蛋糕块，开销为 1 。
     * 沿着水平线 1 切开 2 x 1 的蛋糕块，开销为 3 。
     * 沿着水平线 1 切开 2 x 1 的蛋糕块，开销为 3 。
     * 总开销为 5 + 1 + 1 + 3 + 3 = 13 。
     * 示例 2：
     * 输入：m = 2, n = 2, horizontalCut = [7], verticalCut = [4]
     * 输出：15
     * 解释：
     * 沿着水平线 0 切开蛋糕，开销为 7 。
     * 沿着垂直线 0 切开 1 x 2 的蛋糕块，开销为 4 。
     * 沿着垂直线 0 切开 1 x 2 的蛋糕块，开销为 4 。
     * 总开销为 7 + 4 + 4 = 15 。
     * 提示：
     * 1 <= m, n <= 20
     * horizontalCut.length == m - 1
     * verticalCut.length == n - 1
     * 1 <= horizontalCut[i], verticalCut[i] <= 10^3
     */
    public static int minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        // 每次选择横向和纵向数组中较大的做切割，若是横切，花费=h[i]*纵向维度，然后横向维度+1；若是纵切，花费=v[i]*横向维度，然后纵向维度+1
        // 之后再枚举剩余的唯一数组，直到两数组枚举完，花费总和即为答案
        Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);
        int dh = 1, dv = 1; // 横向维度和纵向维度
        int ph = horizontalCut.length - 1, pv = verticalCut.length - 1;
        int ans = 0;
        while (ph >= 0 && pv >= 0) {
            if (horizontalCut[ph] > verticalCut[pv]) {
                ans += horizontalCut[ph] * dv;
                ph--;
                dh++;
            } else {
                ans += verticalCut[pv] * dh;
                pv--;
                dv++;
            }
        }
        // 下面量循环只会发生一个
        while (ph >= 0) {
            ans += horizontalCut[ph] * dv;
            ph--;
        }
        while (pv >= 0) {
            ans += verticalCut[pv] * dh;
            pv--;
        }
        return ans;
    }

    /**
     * 2680. 最大或值
     * 给你一个下标从 0 开始长度为 n 的整数数组 nums 和一个整数 k 。每一次操作中，你可以选择一个数并将它乘 2 。
     * 你最多可以进行 k 次操作，请你返回 nums[0] | nums[1] | ... | nums[n - 1] 的最大值。
     * a | b 表示两个整数 a 和 b 的 按位或 运算。
     * 示例 1：
     * 输入：nums = [12,9], k = 1
     * 输出：30
     * 解释：如果我们对下标为 1 的元素进行操作，新的数组为 [12,18] 。此时得到最优答案为 12 和 18 的按位或运算的结果，也就是 30 。
     * 示例 2：
     * 输入：nums = [8,1,2], k = 2
     * 输出：35
     * 解释：如果我们对下标 0 处的元素进行操作，得到新数组 [32,1,2] 。此时得到最优答案为 32|1|2 = 35 。
     * 提示：
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= 15
     */
    public long maximumOr(int[] nums, int k) {
        // 前缀后缀或运算和
        int n = nums.length;
        long[] sufOr = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            sufOr[i] = sufOr[i + 1] | nums[i];
        }
        long ans = 0, preOr = 0;
        for (int i = 0; i < n; i++) {
            long cur = preOr | ((long) nums[i] << k) | sufOr[i + 1];
            ans = Math.max(ans, cur);
            preOr |= nums[i];
        }
        return ans;
    }

    /**
     * 2747. 统计没有收到请求的服务器数目
     * 给你一个整数 n ，表示服务器的总数目，再给你一个下标从 0 开始的 二维 整数数组 logs ，
     * 其中 logs[i] = [server_id, time] 表示 id 为 server_id 的服务器在 time 时收到了一个请求。
     * 同时给你一个整数 x 和一个下标从 0 开始的整数数组 queries  。
     * 请你返回一个长度等于 queries.length 的数组 arr ，其中 arr[i] 表示在时间区间 [queries[i] - x, queries[i]] 内没有收到请求的服务器数目。
     * 注意时间区间是个闭区间。
     * 示例 1：
     * 输入：n = 3, logs = [[1,3],[2,6],[1,5]], x = 5, queries = [10,11]
     * 输出：[1,2]
     * 解释：
     * 对于 queries[0]：id 为 1 和 2 的服务器在区间 [5, 10] 内收到了请求，所以只有服务器 3 没有收到请求。
     * 对于 queries[1]：id 为 2 的服务器在区间 [6,11] 内收到了请求，所以 id 为 1 和 3 的服务器在这个时间段内没有收到请求。
     * 示例 2：
     * 输入：n = 3, logs = [[2,4],[2,1],[1,2],[3,1]], x = 2, queries = [3,4]
     * 输出：[0,1]
     * 解释：
     * 对于 queries[0]：区间 [1, 3] 内所有服务器都收到了请求。
     * 对于 queries[1]：只有 id 为 3 的服务器在区间 [2,4] 内没有收到请求。
     * 提示：
     * 1 <= n <= 10^5
     * 1 <= logs.length <= 10^5
     * 1 <= queries.length <= 10^5
     * logs[i].length == 2
     * 1 <= logs[i][0] <= n
     * 1 <= logs[i][1] <= 10^6
     * 1 <= x <= 10^5
     * x < queries[i] <= 10^6
     */
    // *
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int nq = queries.length;
        Integer[] indexs = new Integer[nq];
        Arrays.setAll(indexs, i -> i);
        Arrays.sort(indexs, Comparator.comparingInt(a -> queries[a]));
        Arrays.sort(logs, Comparator.comparingInt(a -> a[1])); // 按照 time 排序

        int[] ans = new int[nq], cnt = new int[n + 1];
        int outOfRange = n, left = 0, right = 0, nl = logs.length;
        for (int i : indexs) {
            while (right < nl && logs[right][1] <= queries[i]) // 进入窗口
                if (cnt[logs[right++][0]]++ == 0)
                    outOfRange--;
            while (left < nl && logs[left][1] < queries[i] - x) // 离开窗口
                if (--cnt[logs[left++][0]] == 0)
                    outOfRange++;
            ans[i] = outOfRange;
        }
        return ans;
    }

    /**
     * 201. 数字范围按位与
     * 给你两个整数 left 和 right ，表示区间 [left, right] ，返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
     * 示例 1：
     * 输入：left = 5, right = 7
     * 输出：4
     * 示例 2：
     * 输入：left = 0, right = 0
     * 输出：0
     * 示例 3：
     * 输入：left = 1, right = 2147483647
     * 输出：0
     * 提示：
     * 0 <= left <= right <= 2^31 - 1
     */
    public int rangeBitwiseAnd(int left, int right) {
        // BK算法
        while (left < right) {
            right &= (right - 1);
        }
        return right;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * 90. 子集 II
     * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的 子集（幂集）。
     * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
     * 示例 1：
     * 输入：nums = [1,2,2]
     * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
     * 示例 2：
     * 输入：nums = [0]
     * 输出：[[],[0]]
     * 提示：
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     */
    List<Integer> t = new ArrayList<>();
    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        dfs(false, 0, nums);
        return ans;
    }

    public void dfs(boolean choosePre, int cur, int[] nums) {
        if (cur == nums.length) {
            ans.add(new ArrayList<>(t));
            return;
        }
        dfs(false, cur + 1, nums);
        if (!choosePre && cur > 0 && nums[cur - 1] == nums[cur]) {
            return;
        }
        t.add(nums[cur]);
        dfs(true, cur + 1, nums);
        t.remove(t.size() - 1);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(minimumCost(6, 3, new int[]{2, 3, 2, 3, 1}, new int[]{1, 2}));
    }
}
