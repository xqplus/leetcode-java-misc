package io.github.xqplus.leetcode;

import java.util.*;

public class Medium1 {

    /**
     * 2640. 一个数组所有前缀的分数
     * 定义一个数组 arr 的 转换数组 conver 为：
     * conver[i] = arr[i] + max(arr[0..i])，其中 max(arr[0..i]) 是满足 0 <= j <= i 的所有 arr[j] 中的最大值。
     * 定义一个数组 arr 的 分数 为 arr 转换数组中所有元素的和。
     * 给你一个下标从 0 开始长度为 n 的整数数组 nums ，请你返回一个长度为 n 的数组 ans ，其中 ans[i]是前缀 nums[0..i] 的分数。
     * 示例 1：
     * 输入：nums = [2,3,7,5,10]
     * 输出：[4,10,24,36,56]
     * 解释：
     * 对于前缀 [2] ，转换数组为 [4] ，所以分数为 4 。
     * 对于前缀 [2, 3] ，转换数组为 [4, 6] ，所以分数为 10 。
     * 对于前缀 [2, 3, 7] ，转换数组为 [4, 6, 14] ，所以分数为 24 。
     * 对于前缀 [2, 3, 7, 5] ，转换数组为 [4, 6, 14, 12] ，所以分数为 36 。
     * 对于前缀 [2, 3, 7, 5, 10] ，转换数组为 [4, 6, 14, 12, 20] ，所以分数为 56 。
     * 示例 2：
     * 输入：nums = [1,1,2,4,8,16]
     * 输出：[2,4,8,16,32,64]
     * 解释：
     * 对于前缀 [1] ，转换数组为 [2] ，所以分数为 2 。
     * 对于前缀 [1, 1]，转换数组为 [2, 2] ，所以分数为 4 。
     * 对于前缀 [1, 1, 2]，转换数组为 [2, 2, 4] ，所以分数为 8 。
     * 对于前缀 [1, 1, 2, 4]，转换数组为 [2, 2, 4, 8] ，所以分数为 16 。
     * 对于前缀 [1, 1, 2, 4, 8]，转换数组为 [2, 2, 4, 8, 16] ，所以分数为 32 。
     * 对于前缀 [1, 1, 2, 4, 8, 16]，转换数组为 [2, 2, 4, 8, 16, 32] ，所以分数为 64 。
     * 提示：
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     */
    public static long[] findPrefixScore(int[] nums) {
        long[] ans = new long[nums.length];
        ans[0] = nums[0] + nums[0];
        long prev = ans[0], max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
            ans[i] = prev + nums[i] + max;
            prev = ans[i];
        }
        return ans;
    }

    /**
     * 2202. K 次操作后最大化顶端元素
     * 给你一个下标从 0 开始的整数数组 nums ，它表示一个 堆 ，其中 nums[0] 是堆顶的元素。
     * 每一次操作中，你可以执行以下操作 之一 ：
     * 如果堆非空，那么 删除 堆顶端的元素。
     * 如果存在 1 个或者多个被删除的元素，你可以从它们中选择任何一个，添加 回堆顶，这个元素成为新的堆顶元素。
     * 同时给你一个整数 k ，它表示你总共需要执行操作的次数。
     * 请你返回 恰好 执行 k 次操作以后，堆顶元素的 最大值 。如果执行完 k 次操作以后，堆一定为空，请你返回 -1 。
     * 示例 1：
     * 输入：nums = [5,2,2,4,0,6], k = 4
     * 输出：5
     * 解释：
     * 4 次操作后，堆顶元素为 5 的方法之一为：
     * - 第 1 次操作：删除堆顶元素 5 ，堆变为 [2,2,4,0,6] 。
     * - 第 2 次操作：删除堆顶元素 2 ，堆变为 [2,4,0,6] 。
     * - 第 3 次操作：删除堆顶元素 2 ，堆变为 [4,0,6] 。
     * - 第 4 次操作：将 5 添加回堆顶，堆变为 [5,4,0,6] 。
     * 注意，这不是最后堆顶元素为 5 的唯一方式。但可以证明，4 次操作以后 5 是能得到的最大堆顶元素。
     * 示例 2：
     * 输入：nums = [2], k = 1
     * 输出：-1
     * 解释：
     * 第 1 次操作中，我们唯一的选择是将堆顶元素弹出堆。
     * 由于 1 次操作后无法得到一个非空的堆，所以我们返回 -1 。
     * 提示：
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i], k <= 10^9
     */
    public int maximumTop(int[] nums, int k) {
        int n = nums.length, ans = -1;
        if (n == 1) {
            if (k % 2 == 0) {
                ans = nums[0];
            }
        } else {
            for (int i = 0; i < Math.min(n, k + 1); i++) {
                if (i != k - 1) {
                    ans = Math.max(ans, nums[i]);
                }
            }
        }
        return ans;
    }

    /**
     * 474. 一和零
     * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
     * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
     * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
     * 示例 1：
     * 输入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3
     * 输出：4
     * 解释：最多有 5 个 0 和 3 个 1 的最大子集是 {"10","0001","1","0"} ，因此答案是 4 。
     * 其他满足题意但较小的子集包括 {"0001","1"} 和 {"10","1","0"} 。{"111001"} 不满足题意，因为它含 4 个 1 ，大于 n 的值 3 。
     * 示例 2：
     * 输入：strs = ["10", "0", "1"], m = 1, n = 1
     * 输出：2
     * 解释：最大的子集是 {"0", "1"} ，所以答案是 2 。
     * 提示：
     * 1 <= strs.length <= 600
     * 1 <= strs[i].length <= 100
     * strs[i] 仅由 '0' 和 '1' 组成
     * 1 <= m, n <= 100
     */
    public static int findMaxForm(String[] strs, int m, int n) {
        int ans = 0;
        // dp[i][j]表示累计i个0,j个1时最大子集数量
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int n0 = 0, n1 = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '0') {
                    n0++;
                } else {
                    n1++;
                }
            }
            if (n0 > m || n1 > n) {
                continue;
            }
            for (int i = m - n0; i >= 0; i--) {
                for (int j = n - n1; j >= 0; j--) {
                    if (dp[i][j] > 0 || (i == 0 && j == 0)) {
                        dp[i + n0][j + n1] = Math.max(dp[i + n0][j + n1], dp[i][j] + 1);
                        ans = Math.max(ans, dp[i + n0][j + n1]);
                    }
                }
            }
        }
        return ans;
    }

    /**
     * LCR 107. 01 矩阵
     * 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
     * 两个相邻元素间的距离为 1 。
     * 示例 1：
     * 0 0 0
     * 0 1 0
     * 0 0 0
     * 输入：mat = [[0,0,0],[0,1,0],[0,0,0]]
     * 输出：[[0,0,0],[0,1,0],[0,0,0]]
     * 示例 2：
     * 0 0 0
     * 0 1 0
     * 1 1 1
     * 输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
     * 输出：[[0,0,0],[0,1,0],[1,2,1]]
     * 提示：
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 10^4
     * 1 <= m * n <= 10^4
     * mat[i][j] is either 0 or 1.
     * mat 中至少有一个 0
     */
    static int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] ans = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int d = 0; d < 4; d++) {
                int x = cur[0] + dirs[d][0];
                int y = cur[1] + dirs[d][1];
                if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y]) {
                    ans[x][y] = ans[cur[0]][cur[1]] + 1;
                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
        }
        return ans;
    }

    /**
     * 3067. 在带权树网络中统计可连接服务器对数目
     * 给你一棵无根带权树，树中总共有 n 个节点，分别表示 n 个服务器，服务器从 0 到 n - 1 编号。
     * 同时给你一个数组 edges ，其中 edges[i] = [ai, bi, weighti] 表示节点 ai 和 bi 之间有一条双向边，边的权值为 weighti 。
     * 再给你一个整数 signalSpeed 。
     * 如果两台服务器 a 和 b 是通过服务器 c 可连接的，则：
     * a < b ，a != c 且 b != c 。
     * // a < b < c | c < a < b | a < c < b
     * 从 c 到 a 的距离是可以被 signalSpeed 整除的。
     * 从 c 到 b 的距离是可以被 signalSpeed 整除的。
     * 从 c 到 b 的路径与从 c 到 a 的路径没有任何公共边。
     * 请你返回一个长度为 n 的整数数组 count ，其中 count[i] 表示通过服务器 i 可连接 的服务器对的 数目 。
     * 示例 1：
     * 0 --1-- 1 --5-- 2 --13-- 3 --9-- 4 --2-- 5
     * 输入：edges = [[0,1,1],[1,2,5],[2,3,13],[3,4,9],[4,5,2]], signalSpeed = 1
     * 输出：[0,4,6,6,4,0]
     * 解释：由于 signalSpeed 等于 1 ，count[c] 等于所有从 c 开始且没有公共边的路径对数目。
     * 在输入图中，count[c] 等于服务器 c 左边服务器数目乘以右边服务器数目。
     * 示例 2：
     * 输入：edges = [[0,6,3],[6,5,3],[0,3,1],[3,2,7],[3,1,6],[3,4,2]], signalSpeed = 3
     * 输出：[2,0,0,0,0,0,2]
     * 解释：通过服务器 0 ，有 2 个可连接服务器对(4, 5) 和 (4, 6) 。
     * 通过服务器 6 ，有 2 个可连接服务器对 (4, 5) 和 (0, 5) 。
     * 所有服务器对都必须通过服务器 0 或 6 才可连接，所以其他服务器对应的可连接服务器对数目都为 0 。
     * 提示：
     * 2 <= n <= 1000
     * edges.length == n - 1
     * edges[i].length == 3
     * 0 <= ai, bi < n
     * edges[i] = [ai, bi, weighti]
     * 1 <= weighti <= 106
     * 1 <= signalSpeed <= 106
     * 输入保证 edges 构成一棵合法的树。
     */
    public static int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        int n = edges.length + 1;
        List<int[]>[] graph = new ArrayList[n];

        // 先构造图结构
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];

            List<int[]> ues = graph[u];
            if (ues == null) {
                ues = new ArrayList<>();
                graph[u] = ues;
            }
            ues.add(new int[]{v, w});

            List<int[]> ves = graph[v];
            if (ves == null) {
                ves = new ArrayList<>();
                graph[v] = ves;
            }
            ves.add(new int[]{u, w});
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            // 跳过顶点邻接带权边只有一条情况
            if (graph[i].size() > 1) {
                // 计算出所有方向满足条件的顶点数量
                int nes = graph[i].size();
                int[] cnt = new int[nes];
                for (int j = 0; j < nes; j++) {
                    int[] e = graph[i].get(j);
                    cnt[j] = dfs(graph, e[0], e[1], signalSpeed, i);
                }
                // 再求乘积和
                for (int j = 0; j < nes - 1; j++) {
                    for (int k = j + 1; k < nes; k++) {
                        ans[i] += cnt[j] * cnt[k];
                    }
                }
            }
        }
        return ans;
    }

    private static int dfs(List<int[]>[] graph, int i, int totalWeight, int signalSpeed, int from) {
        // 无环图边界顶点只有一条边
        int cnt = totalWeight % signalSpeed == 0 ? 1 : 0;
        if (graph[i].size() == 1) {
            return cnt;
        }
        for (int[] e : graph[i]) {
            if (e[0] != from) {
                cnt += dfs(graph, e[0], totalWeight + e[1], signalSpeed, i);
            }
        }
        return cnt;
    }

    /**
     * 2300. 咒语和药水的成功对数
     * 给你两个正整数数组 spells 和 potions ，长度分别为 n 和 m ，其中 spells[i] 表示第 i 个咒语的能量强度，potions[j] 表示第 j 瓶药水的能量强度。
     * 同时给你一个整数 success 。一个咒语和药水的能量强度 相乘 如果 大于等于 success ，那么它们视为一对 成功 的组合。
     * 请你返回一个长度为 n 的整数数组 pairs，其中 pairs[i] 是能跟第 i 个咒语成功组合的 药水 数目。
     * 示例 1：
     * 输入：spells = [5,1,3], potions = [1,2,3,4,5], success = 7
     * 输出：[4,0,3]
     * 解释：
     * - 第 0 个咒语：5 * [1,2,3,4,5] = [5,10,15,20,25] 。总共 4 个成功组合。
     * - 第 1 个咒语：1 * [1,2,3,4,5] = [1,2,3,4,5] 。总共 0 个成功组合。
     * - 第 2 个咒语：3 * [1,2,3,4,5] = [3,6,9,12,15] 。总共 3 个成功组合。
     * 所以返回 [4,0,3] 。
     * 示例 2：
     * 输入：spells = [3,1,2], potions = [8,5,8], success = 16
     * 输出：[2,0,2]
     * 解释：
     * - 第 0 个咒语：3 * [8,5,8] = [24,15,24] 。总共 2 个成功组合。
     * - 第 1 个咒语：1 * [8,5,8] = [8,5,8] 。总共 0 个成功组合。
     * - 第 2 个咒语：2 * [8,5,8] = [16,10,16] 。总共 2 个成功组合。
     * 所以返回 [2,0,2] 。
     * 提示：
     * n == spells.length
     * m == potions.length
     * 1 <= n, m <= 10^5
     * 1 <= spells[i], potions[i] <= 10^5
     * 1 <= success <= 10^10
     */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        // 先对potions排序，遍历spells，确定所需potion的最小值，二分查找位置
        int n = spells.length, m = potions.length;
        Arrays.sort(potions);
        for (int i = 0; i < n; i++) {
            int spell = spells[i];
            long minPotion = (success + spell - 1) / spell;
            int left = 0, right = m - 1, idx = m;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (potions[mid] >= minPotion) {
                    right = mid - 1;
                    idx = mid;
                } else {
                    left = mid + 1;
                }
            }
            spells[i] = m - idx;
        }
        return spells;
    }

    /**
     * 3164. 优质数对的总数 II
     * 给你两个整数数组 nums1 和 nums2，长度分别为 n 和 m。同时给你一个正整数 k。
     * 如果 nums1[i] 可以被 nums2[j] * k 整除，则称数对 (i, j) 为 优质数对（0 <= i <= n - 1, 0 <= j <= m - 1）。
     * 返回 优质数对 的总数。
     * 示例 1：
     * 输入：nums1 = [1,3,4], nums2 = [1,3,4], k = 1
     * 输出：5
     * 解释：
     * 5个优质数对分别是 (0, 0), (1, 0), (1, 1), (2, 0), 和 (2, 2)。
     * 示例 2：
     * 输入：nums1 = [1,2,4,12], nums2 = [2,4], k = 3
     * 输出：2
     * 解释：
     * 2个优质数对分别是 (3, 0) 和 (3, 1)。
     * 提示：
     * 1 <= n, m <= 10^5
     * 1 <= nums1[i], nums2[j] <= 10^6
     * 1 <= k <= 10^3
     */
    public static long numberOfPairs(int[] nums1, int[] nums2, int k) {
        Map<Integer, Integer> counts1 = new HashMap<>();
        int max1 = 0;
        for (int i : nums1) {
            max1 = Math.max(max1, i);
            counts1.put(i, counts1.getOrDefault(i, 0) + 1);
        }
        Map<Integer, Integer> counts2 = new HashMap<>();
        int min2 = 1000000001;
        for (int i : nums2) {
            int key = i * k;
            min2 = Math.min(min2, key);
            counts2.put(key, counts2.getOrDefault(key, 0) + 1);
        }
        if (max1 < min2) {
            return 0;
        }
        long ans = 0;
        for (Map.Entry<Integer, Integer> entry1 : counts1.entrySet()) {
            for (Map.Entry<Integer, Integer> entry2 : counts2.entrySet()) {
                if (entry1.getKey() < entry2.getKey()) {
                    continue;
                }
                if (entry1.getKey() % entry2.getKey() == 0) {
                    ans += (long) entry1.getValue() * entry2.getValue();
                }
            }
        }
        return ans;
    }

    /**
     * 129. 求根节点到叶节点数字之和
     * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
     * 每条从根节点到叶节点的路径都代表一个数字：
     * 例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
     * 计算从根节点到叶节点生成的 所有数字之和 。
     * 叶节点 是指没有子节点的节点。
     * 示例 1：
     * 输入：root = [1,2,3]
     * 输出：25
     * 解释：
     * 从根到叶子节点路径 1->2 代表数字 12
     * 从根到叶子节点路径 1->3 代表数字 13
     * 因此，数字总和 = 12 + 13 = 25
     * 示例 2：
     * 输入：root = [4,9,0,5,1]
     * 输出：1026
     * 解释：
     * 从根到叶子节点路径 4->9->5 代表数字 495
     * 从根到叶子节点路径 4->9->1 代表数字 491
     * 从根到叶子节点路径 4->0 代表数字 40
     * 因此，数字总和 = 495 + 491 + 40 = 1026
     * 提示：
     * 树中节点的数目在范围 [1, 1000] 内
     * 0 <= Node.val <= 9
     * 树的深度不超过 10
     */
    public int sumNumbers(TreeNode root) {
        return dfs1(root, 0);
    }

    private int dfs1(TreeNode node, int num) {
        num = num * 10 + node.val;
        if (node.left == null && node.right == null) {
            return num;
        }
        int left = 0, right = 0;
        if (node.left != null) {
            left = dfs1(node.left, num);
        }
        if (node.right != null) {
            right = dfs1(node.right, num);
        }
        return left + right;
    }

    /**
     * 686. 重复叠加字符串匹配
     * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
     * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
     * 示例 1：
     * 输入：a = "abcd", b = "cdabcdab"
     * 输出：3
     * 解释：a 重复叠加三遍后为 "abcdabcdabcd", 此时 b 是其子串。
     * 示例 2：
     * 输入：a = "a", b = "aa"
     * 输出：2
     * 示例 3：
     * 输入：a = "a", b = "a"
     * 输出：1
     * 示例 4：
     * 输入：a = "abc", b = "wxyz"
     * 输出：-1
     * 提示：
     * 1 <= a.length <= 10^4
     * 1 <= b.length <= 10^4
     * a 和 b 由小写英文字母组成
     */
    public int repeatedStringMatch(String a, String b) {
        int na = a.length(), nb = b.length();
        // na >= nb, 那么可能的情况是 b是a或者两个a的字串
        // na < nb, b中存在 nb / na个a, 最大加上前后各一个a的字串
        // 所以 a的重复字串最大只能是 (nb/na+2)*na
        int maxLen = (nb / na + 2) * na;
        StringBuilder sb = new StringBuilder();
        int ans = 0;
        while (sb.length() < maxLen) {
            sb.append(a);
            ans++;
            if (sb.indexOf(b) != -1) {
                return ans;
            }
        }
        return -1;
    }

    /**
     * 1171. 从链表中删去总和值为零的连续节点
     * 给你一个链表的头节点 head，请你编写代码，反复删去链表中由 总和 值为 0 的连续节点组成的序列，直到不存在这样的序列为止。
     * 删除完毕后，请你返回最终结果链表的头节点。
     * 你可以返回任何满足题目要求的答案。
     * （注意，下面示例中的所有序列，都是对 ListNode 对象序列化的表示。）
     * 示例 1：
     * 输入：head = [1,2,-3,3,1]
     * 输出：[3,1]
     * 提示：答案 [1,2,1] 也是正确的。
     * 示例 2：
     * 输入：head = [1,2,3,-3,4]
     * 输出：[1,2,4]
     * 示例 3：
     * 输入：head = [1,2,3,-3,-2]
     * 输出：[1]
     * 提示：
     * 给你的链表中可能有 1 到 1000 个节点。
     * 对于链表中的每个节点，节点的值：-1000 <= node.val <= 1000.
     */
    public ListNode removeZeroSumSublists(ListNode head) {
        // 1 2 -3 3 1
        // 0 1 3 0 3 4
        // 0 ->3 -> 1

        //   1 2 3 -3 4
        // 0 1 3 6 3 7
        //
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        Map<Integer, ListNode> map = new HashMap<>();
        ListNode cur = dummy;
        int preSum = 0;
        while (cur != null) {
            preSum += cur.val;
            map.put(preSum, cur);
            cur = cur.next;
        }
        cur = dummy;
        preSum = 0;
        while (cur != null) {
            preSum += cur.val;
            if (cur != map.get(preSum)) {
                cur.next = map.get(preSum).next;
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    /**
     * 62. 不同路径
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     * 示例 1：
     * 输入：m = 3, n = 7
     * 输出：28
     * 示例 2：
     * $ #
     * # #
     * # %
     * 输入：m = 3, n = 2
     * 输出：3
     * 解释：
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向下
     * 示例 3：
     * 输入：m = 7, n = 3
     * 输出：28
     * 示例 4：
     * 输入：m = 3, n = 3
     * 输出：6
     * 提示：
     * 1 <= m, n <= 100
     * 题目数据保证答案小于等于 2 * 10^9
     */
    public static int uniquePaths(int m, int n) {
        // bfs + dp
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                } else {
                    if (i - 1 >= 0) {
                        dp[i][j] += dp[i - 1][j];
                    }
                    if (j - 1 >= 0) {
                        dp[i][j] += dp[i][j - 1];
                    }
                }
            }
        }
        return dp[m - 1][n - 1];

//        Queue<int[]> queue = new LinkedList<>();
//        if (m > 1) {
//            queue.offer(new int[]{1, 0});
//        }
//        if (n > 1) {
//            queue.offer(new int[]{0, 1});
//        }
//        while (!queue.isEmpty()) {
//            int[] pos = queue.poll();
//            int x = pos[0], y = pos[1];
//            if (dp[x][y] > 0) {
//                continue;
//            }
//            if (x - 1 >= 0) {
//                dp[x][y] += dp[x - 1][y];
//            }
//            if (y - 1 >= 0) {
//                dp[x][y] += dp[x][y - 1];
//            }
//            if (x + 1 < m) {
//                queue.offer(new int[]{x + 1, y});
//            }
//            if (y + 1 < n) {
//                queue.offer(new int[]{x, y + 1});
//            }
//        }
//        return dp[m - 1][n - 1];
    }

    /**
     * 2275. 按位与结果大于零的最长组合
     * 对数组 nums 执行 按位与 相当于对数组 nums 中的所有整数执行 按位与 。
     * 例如，对 nums = [1, 5, 3] 来说，按位与等于 1 & 5 & 3 = 1 。
     * 同样，对 nums = [7] 而言，按位与等于 7 。
     * 给你一个正整数数组 candidates 。计算 candidates 中的数字每种组合下 按位与 的结果。
     * 返回按位与结果大于 0 的 最长 组合的长度。
     * 示例 1：
     * 输入：candidates = [16,17,71,62,12,24,14]
     * 输出：4
     * 解释：组合 [16,17,62,24] 的按位与结果是 16 & 17 & 62 & 24 = 16 > 0 。
     * 组合长度是 4 。
     * 可以证明不存在按位与结果大于 0 且长度大于 4 的组合。
     * 注意，符合长度最大的组合可能不止一种。
     * 例如，组合 [62,12,24,14] 的按位与结果是 62 & 12 & 24 & 14 = 8 > 0 。
     * 示例 2：
     * 输入：candidates = [8,8]
     * 输出：2
     * 解释：最长组合是 [8,8] ，按位与结果 8 & 8 = 8 > 0 。
     * 组合长度是 2 ，所以返回 2 。
     * 提示：
     * 1 <= candidates.length <= 10^5
     * 1 <= candidates[i] <= 10^7
     */
    public static int largestCombination(int[] candidates) {
        int[] arr = new int[24]; // 10^7 -> 24bit
        for (int candidate : candidates) {
            int i = 0;
            while (candidate > 0) {
                if ((candidate & 1) == 1) {
                    arr[i]++;
                }
                candidate >>= 1;
                i++;
            }
        }
        int ans = 0;
        for (int i : arr) {
            ans = Math.max(ans, i);
        }
        return ans;
    }

    /**
     * 142. 环形链表 II
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
     * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
     * 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * 不允许修改 链表。
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：返回索引为 1 的链表节点
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     * 示例 2：
     * 输入：head = [1,2], pos = 0
     * 输出：返回索引为 0 的链表节点
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     * 示例 3：
     * 输入：head = [1], pos = -1
     * 输出：返回 null
     * 解释：链表中没有环。
     * 提示：
     * 链表中节点的数目范围在范围 [0, 10^4] 内
     * -10^5 <= Node.val <= 10^5
     * pos 的值为 -1 或者链表中的一个有效索引
     * 进阶：你是否可以使用 O(1) 空间解决此题？
     */
    public static ListNode detectCycle(ListNode head) {
        // 快慢指针
        if (head == null) {
            return null;
        }
        ListNode fast = head, slow = head;
        do {
            fast = fast.next;
            if (fast == null) {
                return null;
            }
            fast = fast.next;
            slow = slow.next;
        } while (fast != null && fast != slow);

        if (fast == null) {
            return null;
        }
        ListNode p = head;
        while (p != fast) {
            p = p.next;
            fast = fast.next;
        }
        return p;
    }

    /**
     * 1302. 层数最深叶子节点的和
     * 给你一棵二叉树的根节点 root ，请你返回 层数最深的叶子节点的和 。
     * 示例 1：
     * 1
     * 2   3
     * 4   5 n  6
     * 7 n n n   n  8
     * 输入：root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
     * 输出：15
     * 示例 2：
     * 输入：root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
     * 输出：19
     * 提示：
     * 树中节点数目在范围 [1, 10^4] 之间。
     * 1 <= Node.val <= 100
     */
    public int deepestLeavesSum(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        boolean isLastLevel = root.left == null && root.right == null;
        int ans = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            if (isLastLevel) {
                for (int i = 0; i < size; i++) {
                    ans += q.poll().val;
                }
                break;
            }
            isLastLevel = true;
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) {
                    q.add(node.left);
                    if (isLastLevel && (node.left.left != null || node.left.right != null)) {
                        isLastLevel = false;
                    }
                }
                if (node.right != null) {
                    q.add(node.right);
                    if (isLastLevel && (node.right.left != null || node.right.right != null)) {
                        isLastLevel = false;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * 970. 强整数
     * 给定三个整数 x 、 y 和 bound ，返回 值小于或等于 bound 的所有 强整数 组成的列表 。
     * 如果某一整数可以表示为 xi + yj ，其中整数 i >= 0 且 j >= 0，那么我们认为该整数是一个 强整数 。
     * 你可以按 任何顺序 返回答案。在你的回答中，每个值 最多 出现一次。
     * 示例 1：
     * 输入：x = 2, y = 3, bound = 10
     * 输出：[2,3,4,5,7,9,10]
     * 解释：
     * 2 = 2^0 + 3^0
     * 3 = 2^1 + 3^0
     * 4 = 2^0 + 3^1
     * 5 = 2^1 + 3^1
     * 7 = 2^2 + 3^1
     * 9 = 2^3 + 3^0
     * 10 = 2^0 + 3^2
     * 示例 2：
     * 输入：x = 3, y = 5, bound = 15
     * 输出：[2,4,6,8,10,14]
     * 提示：
     * 1 <= x, y <= 100
     * 0 <= bound <= 10^6
     */
    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        Set<Integer> set = new HashSet<>();
        int px = 1;
        while (px <= bound) {
            int py = 1;
            while (px + py <= bound) {
                set.add(px + py);
                py *= y;
                if (py == 1) {
                    break;
                }
            }
            px *= x;
            if (px == 1) {
                break;
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * 230. 二叉搜索树中第 K 小的元素
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 小的元素（从 1 开始计数）。
     * 示例 1：
     * 输入：root = [3,1,4,null,2], k = 1
     * 输出：1
     * 示例 2：
     * 输入：root = [5,3,6,2,4,null,null,1], k = 3
     * 输出：3
     * 提示：
     * 树中的节点数为 n 。
     * 1 <= k <= n <= 10^4
     * 0 <= Node.val <= 10^4
     * 进阶：如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化算法？
     */
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        kth(root, list);
        return list.get(k - 1);
    }

    private void kth(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        kth(node.left, list);
        list.add(node.val);
        kth(node.right, list);
    }

    /**
     * LCR 081. 组合总和
     * 给定一个无重复元素的正整数数组 candidates 和一个正整数 target ，找出 candidates 中所有可以使数字和为目标数 target 的唯一组合。
     * candidates 中的数字可以无限制重复被选取。如果至少一个所选数字数量不同，则两种组合是不同的。
     * 对于给定的输入，保证和为 target 的唯一组合数少于 150 个。
     * 示例 1：
     * 输入: candidates = [2,3,6,7], target = 7
     * 输出: [[7],[2,2,3]]
     * 示例 2：
     * 输入: candidates = [2,3,5], target = 8
     * 输出: [[2,2,2,2],[2,3,3],[3,5]]
     * 示例 3：
     * 输入: candidates = [2], target = 1
     * 输出: []
     * 示例 4：
     * 输入: candidates = [1], target = 1
     * 输出: [[1]]
     * 示例 5：
     * 输入: candidates = [1], target = 2
     * 输出: [[1,1]]
     * 提示：
     * 1 <= candidates.length <= 30
     * 1 <= candidates[i] <= 200
     * candidate 中的每个元素都是独一无二的。
     * 1 <= target <= 500
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 回溯
        List<List<Integer>> ans = new ArrayList<>();
        comb(candidates, target, 0, 0, ans, new ArrayList<>());
        return ans;
    }

    private void comb(int[] candidates, int target, int sum, int idx, List<List<Integer>> res, List<Integer> path) {
        if (sum >= target) {
            if (sum == target) {
                res.add(new ArrayList<>(path));
            }
            return;
        }
        for (int i = idx; i < candidates.length; i++) {
            path.add(candidates[i]);
            comb(candidates, target, sum + candidates[i], i, res, path);
            path.remove(path.size() - 1);
        }
    }

    /**
     * 54. 螺旋矩阵
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     * 示例 1：
     * 1->2->3
     * |
     * 4->5  6
     * |     |
     * 7<-8<-9
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * 示例 2：
     * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     * 提示：
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 10
     * -100 <= matrix[i][j] <= 100
     */
    private static int[][] dirs1 = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        spir(matrix, visited, 0, 0, 0, ans);
        return ans;
    }

    private static void spir(int[][] matrix, boolean[][] visited, int i, int j, int d, List<Integer> path) {
        path.add(matrix[i][j]);
        visited[i][j] = true; // 优化：可以用 matrix[i][j] = 101 这样修改为题中边界之外的值来标记已访问，空间复杂度缩小到O(1)
        for (int k = 0; k < 4; k++) {
            int di = (d + k) % 4; // d表示方向，右下左上 循环
            int ni = i + dirs1[di][0], nj = j + dirs1[di][1];
            if (ni >= 0 && ni < matrix.length && nj >= 0 && nj < matrix[0].length && !visited[ni][nj]) {
                spir(matrix, visited, ni, nj, di, path);
            }
        }
    }

    /**
     * LCR 185. 统计结果概率
     * 你选择掷出 num 个色子，请返回所有点数总和的概率。
     * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 num 个骰子所能掷出的点数集合中第 i 小的那个的概率。
     * 示例 1：
     * 输入：num = 3
     * 输出：[0.00463,0.01389,0.02778,0.04630,0.06944,0.09722,0.11574,0.12500,0.12500,0.11574,0.09722,0.06944,0.04630,0.02778,0.01389,0.00463]
     * 示例 2：
     * 输入：num = 5
     * 输出:[0.00013,0.00064,0.00193,0.00450,0.00900,0.01620,0.02636,0.03922,0.05401,0.06944,0.08372,0.09452,0.10031,0.10031,0.09452,0.08372,0.06944,0.05401,0.03922,0.02636,0.01620,0.00900,0.00450,0.00193,0.00064,0.00013]
     * 提示：
     * 1 <= num <= 11
     */
    public double[] statisticsProbability(int num) {
        double[] dp = new double[6];
        Arrays.fill(dp, 1.0 / 6.0);
        for (int i = 2; i <= num; i++) {
            double[] tmp = new double[5 * i + 1];
            for (int j = 0; j < dp.length; j++) {
                for (int k = 0; k < 6; k++) {
                    tmp[j + k] += dp[j] / 6.0;
                }
            }
            dp = tmp;
        }
        return dp;
    }

    /**
     * 213. 打家劫舍 II
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
     * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
     * 示例 1：
     * 输入：nums = [2,3,2]
     * 输出：3
     * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     * 示例 2：
     * 输入：nums = [1,2,3,1]
     * 输出：4
     * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 3：
     * 输入：nums = [1,2,3]
     * 输出：3
     * 提示：
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 1000
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n <= 3) {
            int max = 0;
            for (int num : nums) {
                max = Math.max(max, num);
            }
            return max;
        }
        // 第一家选择的情况
        int p1 = nums[0], p2 = p1;
        for (int i = 2; i < n - 1; i++) {
            int max = Math.max(p2, p1 + nums[i]);
            p1 = p2;
            p2 = max;
        }
        // 第一家不选的情况
        int ans = p2;
        p1 = 0;
        p2 = nums[1];
        for (int i = 2; i < n; i++) {
            int max = Math.max(p2, p1 + nums[i]);
            p1 = p2;
            p2 = max;
        }
        return Math.max(ans, p2);
    }

    /**
     * 1680. 连接连续二进制数字
     * 给你一个整数 n ，请你将 1 到 n 的二进制表示连接起来，并返回连接结果对应的 十进制 数字对 10^9 + 7 取余的结果。
     * 示例 1：
     * 输入：n = 1
     * 输出：1
     * 解释：二进制的 "1" 对应着十进制的 1 。
     * 示例 2：
     * 输入：n = 3
     * 输出：27
     * 解释：二进制下，1，2 和 3 分别对应 "1" ，"10" 和 "11" 。
     * 将它们依次连接，我们得到 "11011" ，对应着十进制的 27 。
     * 示例 3：
     * 输入：n = 12
     * 输出：505379714
     * 解释：连接结果为 "1101110010111011110001001101010111100" 。
     * 对应的十进制数字为 118505380540 。
     * 对 10^9 + 7 取余后，结果为 505379714 。
     * 提示：
     * 1 <= n <= 10^5
     */
    public int concatenatedBinary(int n) {
        int k = 1, mod = 1000000007;
        long ans = 0;
        while (k <= n) {
            ans = ans << 32 - Integer.numberOfLeadingZeros(k) | k;
            ans %= mod;
            k++;
        }
        return (int) ans;
    }

    /**
     * 2982. 找出出现至少三次的最长特殊子字符串 II
     * 给你一个仅由小写英文字母组成的字符串 s 。
     * 如果一个字符串仅由单一字符组成，那么它被称为 特殊 字符串。例如，字符串 "abc" 不是特殊字符串，而字符串 "ddd"、"zz" 和 "f" 是特殊字符串。
     * 返回在 s 中出现 至少三次 的 最长特殊子字符串 的长度，如果不存在出现至少三次的特殊子字符串，则返回 -1 。
     * 子字符串 是字符串中的一个连续 非空 字符序列。
     * 示例 1：
     * 输入：s = "aaaa"
     * 输出：2
     * 解释：出现三次的最长特殊子字符串是 "aa"
     * 可以证明最大长度是 2 。
     * 示例 2：
     * 输入：s = "abcdef"
     * 输出：-1
     * 解释：不存在出现至少三次的特殊子字符串。因此返回 -1 。
     * 示例 3：
     * 输入：s = "abcaba"
     * 输出：1
     * 解释：出现三次的最长特殊子字符串是 "a"
     * 可以证明最大长度是 1 。
     * 提示：
     * 3 <= s.length <= 5 * 10^5
     * s 仅由小写英文字母组成。
     */
    public int maximumLength(String s) {
        // 统计每个字符出现的三个最长的特殊子字符串长度
        int[][] counts = new int[26][3];
        char[] cs = s.toCharArray();
        int n = cs.length;
        for (int i = 0, j = 0; i < n; i = j) {
            while (j < n && cs[j] == cs[i]) {
                j++;
            }
            int num = j - i, idx = cs[i] - 'a';
            if (num > counts[idx][0]) {
                counts[idx][2] = counts[idx][1];
                counts[idx][1] = counts[idx][0];
                counts[idx][0] = num;
            } else if (num > counts[idx][1]) {
                counts[idx][2] = counts[idx][1];
                counts[idx][1] = num;
            } else if (num > counts[idx][2]) {
                counts[idx][2] = num;
            }
        }
        int ans = -1;
        // l0 l1 l2
        // l0-2
        // l0==l1 l0-1
        // l0>l1 l1
        // l2
        for (int[] count : counts) {
            int l1 = Math.min(count[0] - 1, count[1]);
            int l = Math.max(count[0] - 2, Math.max(l1, count[2]));
            if (l > 0) {
                ans = Math.max(ans, l);
            }
        }
        return ans;
    }

    /**
     * LCP 62. 交通枢纽
     * 为了缓解「力扣嘉年华」期间的人流压力，组委会在活动期间开设了一些交通专线。path[i] = [a, b] 表示有一条从地点 a通往地点 b 的 单向 交通专线。
     * 若存在一个地点，满足以下要求，我们则称之为 交通枢纽：
     * 所有地点（除自身外）均有一条 单向 专线 直接 通往该地点；
     * 该地点不存在任何 通往其他地点 的单向专线。
     * 请返回交通专线的 交通枢纽。若不存在，则返回 -1。
     * 注意：
     * 对于任意一个地点，至少被一条专线连通。
     * 示例 1：
     * 输入：path = [[0,1],[0,3],[1,3],[2,0],[2,3]]
     * 输出：3
     * 解释：如下图所示： 地点 0,1,2 各有一条通往地点 3 的交通专线， 且地点 3 不存在任何通往其他地点的交通专线。image.png
     * 示例 2：
     * 输入：path = [[0,3],[1,0],[1,3],[2,0],[3,0],[3,2]]
     * 输出：-1
     * 解释：如下图所示：不存在满足 交通枢纽 的地点。image.png
     * 提示：
     * 1 <= path.length <= 1000
     * 0 <= path[i][0], path[i][1] <= 1000
     * path[i][0] 与 path[i][1] 不相等
     */
    public static int transportationHub(int[][] path) {
        // 出度为0，入度为n-1的地点即为交通枢纽
        int[][] dir = new int[1001][2]; // [][0]为入度，[][1]为出度
        Set<Integer> set = new HashSet<>();
        for (int[] p : path) {
            dir[p[0]][1]++;
            dir[p[1]][0]++;
            set.add(p[0]);
            set.add(p[1]);
        }
        int n = set.size();
        for (Integer d : set) {
            if (dir[d][0] == n - 1 && dir[d][1] == 0) {
                return d;
            }
        }
        return -1;
    }

    /**
     * 1975. 最大方阵和
     * 给你一个 n x n 的整数方阵 matrix 。你可以执行以下操作 任意次 ：
     * 选择 matrix 中 相邻 两个元素，并将它们都 乘以 -1 。
     * 如果两个元素有 公共边 ，那么它们就是 相邻 的。
     * 你的目的是 最大化 方阵元素的和。请你在执行以上操作之后，返回方阵的 最大 和。
     * 示例 1：
     * 1  -1
     * -1  1
     * 输入：matrix = [[1,-1],[-1,1]]
     * 输出：4
     * 解释：我们可以执行以下操作使和等于 4 ：
     * - 将第一行的 2 个元素乘以 -1 。
     * - 将第一列的 2 个元素乘以 -1 。
     * 示例 2：
     *  1  2  3
     * -1 -2 -3
     *  1  2  3
     * 输入：matrix = [[1,2,3],[-1,-2,-3],[1,2,3]]
     * 输出：16
     * 解释：我们可以执行以下操作使和等于 16 ：
     * - 将第二行的最后 2 个元素乘以 -1 。
     * 提示：
     * n == matrix.length == matrix[i].length
     * 2 <= n <= 250
     * -10^5 <= matrix[i][j] <= 10^5
     */
    public long maxMatrixSum(int[][] matrix) {
        // 设矩阵负数个数为n,那么经过任意次变化后，数量一定为 n & 1，如果最后存在一个负数，那么一定是绝对值最小的数
        long sumAbs = 0;
        int n = matrix.length, neg = 0, minAbs = 100001;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] < 0) {
                    neg++;
                }
                sumAbs += Math.abs(matrix[i][j]);
                minAbs = Math.min(minAbs, Math.abs(matrix[i][j]));
            }
        }
        return (neg & 1) == 0 ? sumAbs : sumAbs - (minAbs << 1);
    }

    /**
     * 2787. 将一个数字表示成幂的和的方案数
     * 给你两个 正 整数 n 和 x 。
     * 请你返回将 n 表示成一些 互不相同 正整数的 x 次幂之和的方案数。
     * 换句话说，你需要返回互不相同整数 [n1, n2, ..., nk] 的集合数目，满足 n = n1x + n2x + ... + nkx 。
     * 由于答案可能非常大，请你将它对 109 + 7 取余后返回。
     * 比方说，n = 160 且 x = 3 ，一个表示 n 的方法是 n = 2^3 + 3^3 + 5^3 。
     * 示例 1：
     * 输入：n = 10, x = 2
     * 输出：1
     * 解释：我们可以将 n 表示为：n = 3^2 + 1^2 = 10 。
     * 这是唯一将 10 表达成不同整数 2 次方之和的方案。
     * 示例 2：
     * 输入：n = 4, x = 1
     * 输出：2
     * 解释：我们可以将 n 按以下方案表示：
     * - n = 4^1 = 4 。
     * - n = 3^1 + 1^1 = 4 。
     * 提示：
     * 1 <= n <= 300
     * 1 <= x <= 5
     */
    public static int numberOfWays(int n, int x) {
        // n=10 x=2
        // 1^2=1 2^2=4 3^2=9
        List<Integer> pows = new ArrayList<>();
        int k = 1, pow;
        while ((pow = (int) Math.pow(k, x)) <= n) {
            pows.add(pow);
            k++;
        }
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (Integer p : pows) {
            for (int i = n; i >= p; i--) {
                dp[i] += dp[i - p];
            }
        }
        return (int) (dp[n] % 1000000007);
    }

    public static int countSubsets(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1; // 初始化空集
        for (int num : nums) {
            // 从后向前更新，避免覆盖
            for (int s = target; s >= num; s--) {
                dp[s] += dp[s - num];
            }
        }
        return dp[target];
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(countSubsets(new int[]{1, 4, 9}, 10));
        System.out.println(numberOfWays(10, 2));
    }
}
