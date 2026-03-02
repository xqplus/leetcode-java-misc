package io.github.xqplus.leetcode;

import java.util.*;

public class RandomQuestion {

    /**
     * 1424. 对角线遍历 II
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个列表 nums ，里面每一个元素都是一个整数列表。请你依照下面各图的规则，按顺序返回 nums 中对角线上的整数。
     *
     *
     *
     * 示例 1：
     * 1 2 3 10 13 14 15
     * 4 5 6
     * 7 8 9 12
     *
     * 00 | 10 01 | 20 11 02
     * 输入：nums = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,4,2,7,5,3,8,6,9]
     * 示例 2：
     *
     *
     *
     * 输入：nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
     * 输出：[1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
     * 示例 3：
     *
     * 输入：nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
     * 输出：[1,4,2,5,3,8,6,9,7,10,11]
     * 示例 4：
     *
     * 输入：nums = [[1,2,3,4,5,6]]
     * 输出：[1,2,3,4,5,6]
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i].length <= 10^5
     * 1 <= nums[i][j] <= 10^9
     * nums 中最多有 10^5 个数字。
     */
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> row = nums.get(i);
            for (int j = 0; j < row.size(); j++) {
                list.add(new int[]{i, j, row.get(j)});
            }
        }
        list.sort(Comparator.comparingInt((int[] a) -> a[0] + a[1]).thenComparingInt(a -> a[1]));
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i)[2];
        }
        return ans;
    }

    /**
     * 2920. 收集所有金币可获得的最大积分
     * 困难
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 有一棵由 n 个节点组成的无向树，以 0  为根节点，节点编号从 0 到 n - 1 。给你一个长度为 n - 1 的二维 整数 数组 edges ，
     * 其中 edges[i] = [ai, bi] 表示在树上的节点 ai 和 bi 之间存在一条边。另给你一个下标从 0 开始、长度为 n 的数组 coins 和一个整数 k ，
     * 其中 coins[i] 表示节点 i 处的金币数量。
     *
     * 从根节点开始，你必须收集所有金币。要想收集节点上的金币，必须先收集该节点的祖先节点上的金币。
     *
     * 节点 i 上的金币可以用下述方法之一进行收集：
     *
     * 收集所有金币，得到共计 coins[i] - k 点积分。如果 coins[i] - k 是负数，你将会失去 abs(coins[i] - k) 点积分。
     * 收集所有金币，得到共计 floor(coins[i] / 2) 点积分。如果采用这种方法，节点 i 子树中所有节点 j 的金币数 coins[j] 将会减少至 floor(coins[j] / 2) 。
     * 返回收集 所有 树节点的金币之后可以获得的最大积分。
     *
     *
     *
     * 示例 1：
     *
     *
     * 输入：edges = [[0,1],[1,2],[2,3]], coins = [10,10,3,3], k = 5
     * 输出：11
     * 解释：
     * 使用第一种方法收集节点 0 上的所有金币。总积分 = 10 - 5 = 5 。
     * 使用第一种方法收集节点 1 上的所有金币。总积分 = 5 + (10 - 5) = 10 。
     * 使用第二种方法收集节点 2 上的所有金币。所以节点 3 上的金币将会变为 floor(3 / 2) = 1 ，总积分 = 10 + floor(3 / 2) = 11 。
     * 使用第二种方法收集节点 3 上的所有金币。总积分 =  11 + floor(1 / 2) = 11.
     * 可以证明收集所有节点上的金币能获得的最大积分是 11 。
     * 示例 2：
     *
     *
     * 输入：edges = [[0,1],[0,2]], coins = [8,4,4], k = 0
     * 输出：16
     * 解释：
     * 使用第一种方法收集所有节点上的金币，因此，总积分 = (8 - 0) + (4 - 0) + (4 - 0) = 16 。
     *
     *
     * 提示：
     *
     * n == coins.length
     * 2 <= n <= 105
     * 0 <= coins[i] <= 104
     * edges.length == n - 1
     * 0 <= edges[i][0], edges[i][1] < n
     * 0 <= k <= 104
     */
    public int maximumPoints(int[][] edges, int[] coins, int k) {
        // 树形DP + 记忆化搜索
        int n = coins.length;
        List<Integer>[] tree = new ArrayList[n];
        Arrays.setAll(tree, i -> new ArrayList<>());
        for (int[] edge : edges) {
            tree[edge[0]].add(edge[1]);
            tree[edge[1]].add(edge[0]);
        }
        int[][] memo = new int[n][14]; // 2^14 > 10^4
        for (int[] m : memo) {
            Arrays.fill(m, -1); // 表示没计算过
        }
        return dfs(0, 0, -1, k, coins, memo, tree);
    }

    private int dfs(int node, int cntMethod2, int parentNode, int k, int[] coins, int[][] memo, List<Integer>[] tree) {
        if (memo[node][cntMethod2] != -1) {
            return memo[node][cntMethod2];
        }
        int coinWithMethod1 = (coins[node] >> cntMethod2) - k;
        int coinWithMethod2 = coins[node] >> cntMethod2 + 1;
        for (int child : tree[node]) {
            if (child == parentNode) {
                continue;
            }
            coinWithMethod1 += dfs(child, cntMethod2, node, k, coins, memo, tree);
            if (cntMethod2 < 13) {
                coinWithMethod2 += dfs(child, cntMethod2 + 1, node, k, coins, memo, tree);
            }
        }
        memo[node][cntMethod2] = Math.max(coinWithMethod1, coinWithMethod2);
        return memo[node][cntMethod2];
    }

    /**
     * 2108. 找出数组中的第一个回文字符串
     * 简单
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个字符串数组 words ，找出并返回数组中的 第一个回文字符串 。如果不存在满足要求的字符串，返回一个 空字符串 "" 。
     *
     * 回文字符串 的定义为：如果一个字符串正着读和反着读一样，那么该字符串就是一个 回文字符串 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：words = ["abc","car","ada","racecar","cool"]
     * 输出："ada"
     * 解释：第一个回文字符串是 "ada" 。
     * 注意，"racecar" 也是回文字符串，但它不是第一个。
     * 示例 2：
     *
     * 输入：words = ["notapalindrome","racecar"]
     * 输出："racecar"
     * 解释：第一个也是唯一一个回文字符串是 "racecar" 。
     * 示例 3：
     *
     * 输入：words = ["def","ghi"]
     * 输出：""
     * 解释：不存在回文字符串，所以返回一个空字符串。
     *
     *
     * 提示：
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 100
     * words[i] 仅由小写英文字母组成
     */
    public String firstPalindrome(String[] words) {
        for (String word : words) {
            if (isPal(word)) {
                return word;
            }
        }
        return "";
    }

    private boolean isPal(String word) {
        int left = 0, right = word.length() - 1;
        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * 1390. 四因数
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个整数数组 nums，请你返回该数组中恰有四个因数的这些整数的各因数之和。如果数组中不存在满足题意的整数，则返回 0 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：nums = [21,4,7]
     * 输出：32
     * 解释：
     * 21 有 4 个因数：1, 3, 7, 21
     * 4 有 3 个因数：1, 2, 4
     * 7 有 2 个因数：1, 7
     * 答案仅为 21 的所有因数的和。
     * 示例 2:
     *
     * 输入: nums = [21,21]
     * 输出: 64
     * 示例 3:
     *
     * 输入: nums = [1,2,3,4,5]
     * 输出: 0
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 104
     * 1 <= nums[i] <= 105
     */
    public int sumFourDivisors(int[] nums) {
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                ans += map.get(num);
                continue;
            }
            int sum = 1 + num, cnt = 2, k = 2;
            while (k * k < num) { // 用<而不用<=是为了忽略例如 2*2=4这种情况
                if (num % k == 0) {
                    cnt += 2;
                    if (cnt > 4) {
                        break;
                    }
                    sum += k + num / k;
                }
                k++;
            }
            if (cnt == 4) {
                ans += sum;
                map.put(num, sum);
            } else {
                map.put(num, 0);
            }
        }
        return ans;
    }

    /**
     * 3163. 压缩字符串 III
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个字符串 word，请你使用以下算法进行压缩：
     *
     * 从空字符串 comp 开始。当 word 不为空 时，执行以下操作：
     * 移除 word 的最长单字符前缀，该前缀由单一字符 c 重复多次组成，且该前缀长度 最多 为 9 。
     * 将前缀的长度和字符 c 追加到 comp 。
     * 返回字符串 comp 。
     *
     *
     *
     *
     *
     * 示例 1：
     *
     * 输入：word = "abcde"
     *
     * 输出："1a1b1c1d1e"
     *
     * 解释：
     *
     * 初始时，comp = "" 。进行 5 次操作，每次操作分别选择 "a"、"b"、"c"、"d" 和 "e" 作为前缀。
     *
     * 对每个前缀，将 "1" 和对应的字符追加到 comp。
     *
     * 示例 2：
     *
     * 输入：word = "aaaaaaaaaaaaaabb"
     *
     * 输出："9a5a2b"
     *
     * 解释：
     *
     * 初始时，comp = ""。进行 3 次操作，每次操作分别选择 "aaaaaaaaa"、"aaaaa" 和 "bb" 作为前缀。
     *
     * 对于前缀 "aaaaaaaaa"，将 "9" 和 "a" 追加到 comp。
     * 对于前缀 "aaaaa"，将 "5" 和 "a" 追加到 comp。
     * 对于前缀 "bb"，将 "2" 和 "b" 追加到 comp。
     *
     *
     * 提示：
     *
     * 1 <= word.length <= 2 * 105
     * word 仅由小写英文字母组成。
     */
    public String compressedString(String word) {
        StringBuilder sb = new StringBuilder();
        int n = word.length(), cnt = 1;
        char pre = word.charAt(0);
        for (int i = 1; i < n; i++) {
            char c = word.charAt(i);
            if (c != pre || cnt == 9) {
                sb.append(cnt);
                sb.append(pre);
                pre = c;
                cnt = 1;
            } else {
                cnt++;
            }
        }
        sb.append(cnt);
        sb.append(pre);
        return sb.toString();
        // 1, 4, 9, 121, 484, 10201, 12321, 14641, 40804, 44944, 1002001, 1234321, 4008004, 100020001, 102030201, 104060401, 121242121, 123454321, 125686521, 400080004, 404090404, 10000200001, 10221412201, 12102420121, 12345654321, 40000800004, 1000002000001, 1002003002001, 1004006004001, 1020304030201, 1022325232201, 1024348434201, 1210024200121, 1212225222121, 1214428244121, 1232346432321, 1234567654321, 4000008000004, 4004009004004, 100000020000001, 100220141022001, 102012040210201, 102234363432201, 121000242000121, 121242363242121, 123212464212321, 123456787654321, 400000080000004, 10000000200000001, 10002000300020001, 10004000600040001, 10020210401202001, 10022212521222001, 10024214841242001, 10201020402010201, 10203040504030201, 10205060806050201, 10221432623412201, 10223454745432201, 12100002420000121, 12102202520220121, 12104402820440121, 12122232623222121, 12124434743442121, 12321024642012321, 12323244744232321, 12343456865434321, 12345678987654321, 40000000800000004, 40004000900040004, 1000000002000000001, 1000220014100220001, 1002003004003002001, 1002223236323222001, 1020100204020010201, 1020322416142230201, 1022123226223212201, 1022345658565432201, 1210000024200000121, 1210242036302420121, 1212203226223022121, 1212445458545442121, 1232100246420012321, 1232344458544432321, 1234323468643234321, 4000000008000000004
    }

    /**
     * 906. 超级回文数
     * 困难
     * 相关标签
     * premium lock icon
     * 相关企业
     * 如果一个正整数自身是回文数，而且它也是一个回文数的平方，那么我们称这个数为 超级回文数 。
     *
     * 现在，给你两个以字符串形式表示的正整数 left 和 right  ，统计并返回区间 [left, right] 中的 超级回文数 的数目。
     *
     *
     *
     * 示例 1：
     *
     * 输入：left = "4", right = "1000"
     * 输出：4
     * 解释：4、9、121 和 484 都是超级回文数。
     * 注意 676 不是超级回文数：26 * 26 = 676 ，但是 26 不是回文数。
     * 示例 2：
     *
     * 输入：left = "1", right = "2"
     * 输出：1
     *
     *
     * 提示：
     *
     * 1 <= left.length, right.length <= 18
     * left 和 right 仅由数字（0 - 9）组成。
     * left 和 right 不含前导零。
     * left 和 right 表示的整数在区间 [1, 1018 - 1] 内。
     * left 小于等于 right 。
     */
    // size == 70
    private static final long[] record = new long[]{
            1L,
            4L,
            9L,
            121L,
            484L,
            10201L,
            12321L,
            14641L,
            40804L,
            44944L,
            1002001L,
            1234321L,
            4008004L,
            100020001L,
            102030201L,
            104060401L,
            121242121L,
            123454321L,
            125686521L,
            400080004L,
            404090404L,
            10000200001L,
            10221412201L,
            12102420121L,
            12345654321L,
            40000800004L,
            1000002000001L,
            1002003002001L,
            1004006004001L,
            1020304030201L,
            1022325232201L,
            1024348434201L,
            1210024200121L,
            1212225222121L,
            1214428244121L,
            1232346432321L,
            1234567654321L,
            4000008000004L,
            4004009004004L,
            100000020000001L,
            100220141022001L,
            102012040210201L,
            102234363432201L,
            121000242000121L,
            121242363242121L,
            123212464212321L,
            123456787654321L,
            400000080000004L,
            10000000200000001L,
            10002000300020001L,
            10004000600040001L,
            10020210401202001L,
            10022212521222001L,
            10024214841242001L,
            10201020402010201L,
            10203040504030201L,
            10205060806050201L,
            10221432623412201L,
            10223454745432201L,
            12100002420000121L,
            12102202520220121L,
            12104402820440121L,
            12122232623222121L,
            12124434743442121L,
            12321024642012321L,
            12323244744232321L,
            12343456865434321L,
            12345678987654321L,
            40000000800000004L,
            40004000900040004L
    };

    public int superpalindromesInRange(String left, String right) {
        return search(Long.parseLong(right)) - search(Long.parseLong(left));
    }

    private int search(long target) {
        int left = 0, right = 69;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (record[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 1813. 句子相似性 III
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给定两个字符串 sentence1 和 sentence2，每个表示由一些单词组成的一个句子。句子是一系列由 单个 空格分隔的 单词，且开头和结尾没有多余空格。
     * 每个单词都只包含大写和小写英文字母。
     *
     * 如果两个句子 s1 和 s2 ，可以通过往其中一个句子插入一个任意的句子（可以是空句子）而得到另一个句子，那么我们称这两个句子是 相似的 。
     * 注意，插入的句子必须与现有单词用空白隔开。
     *
     * 比方说，
     *
     * s1 = "Hello Jane" 与 s2 = "Hello my name is Jane"，我们可以往 s1 中 "Hello" 和 "Jane" 之间插入 "my name is" 得到 s2 。
     * s1 = "Frog cool" 与 s2 = "Frogs are cool" 不是相似的，因为尽管往 s1 中插入 "s are"，它没有与 "Frog" 用空格隔开。
     * 给你两个句子 sentence1 和 sentence2 ，如果 sentence1 和 sentence2 是 相似 的，请你返回 true ，否则返回 false 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：sentence1 = "My name is Haley", sentence2 = "My Haley"
     * 输出：true
     * 解释：可以往 sentence2 中 "My" 和 "Haley" 之间插入 "name is" ，得到 sentence1 。
     *
     * 示例 2：
     *
     * 输入：sentence1 = "of", sentence2 = "A lot of words"
     * 输出：false
     * 解释：没法往这两个句子中的一个句子只插入一个句子就得到另一个句子。
     *
     * 示例 3：
     *
     * 输入：sentence1 = "Eating right now", sentence2 = "Eating"
     * 输出：true
     * 解释：可以往 sentence2 的结尾插入 "right now" 得到 sentence1 。
     *
     *
     * 提示：
     *
     * 1 <= sentence1.length, sentence2.length <= 100
     * sentence1 和 sentence2 都只包含大小写英文字母和空格。
     * sentence1 和 sentence2 中的单词都只由单个空格隔开。
     */
    public static boolean areSentencesSimilar(String sentence1, String sentence2) {
        int n1 = sentence1.length(), n2 = sentence2.length();
        if (n1 == 0 || n2 == 0) {
            return true;
        }
        if (n1 == n2) {
            return sentence1.equals(sentence2);
        }
        return n1 > n2 ? compare1(sentence1, sentence2) : compare1(sentence2, sentence1);
    }

    private static boolean compare1(String longSen, String shortSen) {
        String[] split1 = longSen.split(" ");
        String[] split2 = shortSen.split(" ");
        int left = 0, right1 = split1.length - 1, right2 = split2.length - 1;
        while (left <= right2 && split1[left].equals(split2[left])) {
            left++;
        }
        while (left <= right2 && split1[right1].equals(split2[right2])) {
            right1--;
            right2--;
        }
        return left > right2;
    }

    /**
     * 1457. 二叉树中的伪回文路径
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一棵二叉树，每个节点的值为 1 到 9 。我们称二叉树中的一条路径是 「伪回文」的，当它满足：路径经过的所有节点值的排列中，存在一个回文序列。
     *
     * 请你返回从根到叶子节点的所有路径中 伪回文 路径的数目。
     *
     *
     *
     * 示例 1：
     *
     *
     *
     * 输入：root = [2,3,1,3,1,null,1]
     * 输出：2
     * 解释：上图为给定的二叉树。总共有 3 条从根到叶子的路径：红色路径 [2,3,3] ，绿色路径 [2,1,1] 和路径 [2,3,1] 。
     *      在这些路径中，只有红色和绿色的路径是伪回文路径，因为红色路径 [2,3,3] 存在回文排列 [3,2,3] ，绿色路径 [2,1,1] 存在回文排列 [1,2,1] 。
     * 示例 2：
     *
     *
     *
     * 输入：root = [2,1,1,1,3,null,null,null,null,null,1]
     * 输出：1
     * 解释：上图为给定二叉树。总共有 3 条从根到叶子的路径：绿色路径 [2,1,1] ，路径 [2,1,3,1] 和路径 [2,1] 。
     *      这些路径中只有绿色路径是伪回文路径，因为 [2,1,1] 存在回文排列 [1,2,1] 。
     * 示例 3：
     *
     * 输入：root = [9]
     * 输出：1
     *
     *
     * 提示：
     *
     * 给定二叉树的节点数目在范围 [1, 105] 内
     * 1 <= Node.val <= 9
     */
    private int ans1 = 0;
    private int[] cnts = new int[10];

    public int pseudoPalindromicPaths(TreeNode root) {
        dfs1(root);
        return ans1;
    }

    private void dfs1(TreeNode node) {
        cnts[node.val]++;
        if (node.left == null && node.right == null) {
            int cntOdd = 0;
            for (int cnt : cnts) {
                if ((cnt & 1) == 1) {
                    cntOdd++;
                }
            }
            if (cntOdd <= 1) {
                ans1++;
            }
            cnts[node.val]--;
            return;
        }
        if (node.left != null) {
            dfs1(node.left);
        }
        if (node.right != null) {
            dfs1(node.right);
        }
        cnts[node.val]--;
    }

    /**
     * 1033. 移动石子直到连续
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 三枚石子放置在数轴上，位置分别为 a，b，c。
     *
     * 每一回合，你可以从两端之一拿起一枚石子（位置最大或最小），并将其放入两端之间的任一空闲位置。
     * 形式上，假设这三枚石子当前分别位于位置 x, y, z 且 x < y < z。那么就可以从位置 x 或者是位置 z 拿起一枚石子，
     * 并将该石子移动到某一整数位置 k 处，其中 x < k < z 且 k != y。
     *
     * 当你无法进行任何移动时，即，这些石子的位置连续时，游戏结束。
     *
     * 要使游戏结束，你可以执行的最小和最大移动次数分别是多少？ 以长度为 2 的数组形式返回答案：answer = [minimum_moves, maximum_moves]
     *
     *
     *
     * 示例 1：
     *
     * 输入：a = 1, b = 2, c = 5
     * 输出：[1, 2]
     * 解释：将石子从 5 移动到 4 再移动到 3，或者我们可以直接将石子移动到 3。
     * 示例 2：
     *
     * 输入：a = 4, b = 3, c = 2
     * 输出：[0, 0]
     * 解释：我们无法进行任何移动。
     *
     *
     * 提示：
     *
     * 1 <= a <= 100
     * 1 <= b <= 100
     * 1 <= c <= 100
     * a != b, b != c, c != a
     */
    public int[] numMovesStones(int a, int b, int c) {
        int[] nums = new int[]{a, b, c};
        Arrays.sort(nums);
        int diff1 = nums[1] - nums[0], diff2 = nums[2] - nums[1];
        int maxDiff = Math.max(diff1, diff2), minDiff = Math.min(diff1, diff2);
        if (maxDiff == 1) {
            return new int[]{0, 0};
        }
        int min = minDiff <= 2 ? 1 : 2;
        return new int[]{min, nums[2] - nums[0] - 2};
    }

    /**
     * 2998. 使 X 和 Y 相等的最少操作次数
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你两个正整数 x 和 y 。
     *
     * 一次操作中，你可以执行以下四种操作之一：
     *
     * 如果 x 是 11 的倍数，将 x 除以 11 。
     * 如果 x 是 5 的倍数，将 x 除以 5 。
     * 将 x 减 1 。
     * 将 x 加 1 。
     * 请你返回让 x 和 y 相等的 最少 操作次数。
     *
     *
     *
     * 示例 1：
     *
     * 输入：x = 26, y = 1
     * 输出：3
     * 解释：我们可以通过以下操作将 26 变为 1 ：
     * 1. 将 x 减 1
     * 2. 将 x 除以 5
     * 3. 将 x 除以 5
     * 将 26 变为 1 最少需要 3 次操作。
     * 示例 2：
     *
     * 输入：x = 54, y = 2
     * 输出：4
     * 解释：我们可以通过以下操作将 54 变为 2 ：
     * 1. 将 x 加 1
     * 2. 将 x 除以 11
     * 3. 将 x 除以 5
     * 4. 将 x 加 1
     * 将 54 变为 2 最少需要 4 次操作。
     * 示例 3：
     *
     * 输入：x = 25, y = 30
     * 输出：5
     * 解释：我们可以通过以下操作将 25 变为 30 ：
     * 1. 将 x 加 1
     * 2. 将 x 加 1
     * 3. 将 x 加 1
     * 4. 将 x 加 1
     * 5. 将 x 加 1
     * 将 25 变为 30 最少需要 5 次操作。
     *
     *
     * 提示：
     *
     * 1 <= x, y <= 10^4
     */
    public static int minimumOperationsToMakeEqual(int x, int y) {
        if (x <= y) {
            return y - x;
        }
        int ans = x - y; // 总操作次数不会超过 x-y
        boolean[] vis = new boolean[x + ans + 1]; // +1 操作至多执行 x-y 次
        vis[x] = true;
        List<Integer> q = Collections.singletonList(x);
        int step = 0;
        while (true) {
            List<Integer> tmp = q;
            q = new ArrayList<>();
            for (int v : tmp) {
                if (v == y) {
                    return Math.min(ans, step);
                }
                if (v < y) {
                    ans = Math.min(ans, step + y - v);
                    continue;
                }
                if (v % 11 == 0 && !vis[v / 11]) {
                    vis[v / 11] = true;
                    q.add(v / 11);
                }
                if (v % 5 == 0 && !vis[v / 5]) {
                    vis[v / 5] = true;
                    q.add(v / 5);
                }
                if (!vis[v - 1]) {
                    vis[v - 1] = true;
                    q.add(v - 1);
                }
                if (!vis[v + 1]) {
                    vis[v + 1] = true;
                    q.add(v + 1);
                }
            }
            step++;
        }
    }

    /**
     * 678. 有效的括号字符串
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个只包含三种字符的字符串，支持的字符类型分别是 '('、')' 和 '*'。请你检验这个字符串是否为有效字符串，如果是 有效 字符串返回 true 。
     *
     * 有效 字符串符合如下规则：
     *
     * 任何左括号 '(' 必须有相应的右括号 ')'。
     * 任何右括号 ')' 必须有相应的左括号 '(' 。
     * 左括号 '(' 必须在对应的右括号之前 ')'。
     * '*' 可以被视为单个右括号 ')' ，或单个左括号 '(' ，或一个空字符串 ""。
     *
     *
     * 示例 1：
     *
     * 输入：s = "()"
     * 输出：true
     * 示例 2：
     *
     * 输入：s = "(*)"
     * 输出：true
     * 示例 3：
     *
     * 输入：s = "(*))"
     * 输出：true
     *
     *
     * 提示：
     *
     * 1 <= s.length <= 100
     * s[i] 为 '('、')' 或 '*'
     */
    public boolean checkValidString(String s) {
        // 用栈解决：维护左括号栈和星号栈，遍历过程中每遇到左括号和星号分别入对应的栈，如果遇到右括号，先后选择左括号、星号有值之一弹出，都无值返回fasle
        // 遍历结束后如果左括号栈还存在值，则需要取星号栈中位于当前栈中每个左括号之后的元素看作右括号，所以栈中应该维护下标
        Deque<Integer> leftStack = new LinkedList<>();
        Deque<Integer> starStack = new LinkedList<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftStack.push(i);
            } else if (c == '*') {
                starStack.push(i);
            } else {
                if (!leftStack.isEmpty()) {
                    leftStack.pop();
                } else if (!starStack.isEmpty()) {
                    starStack.pop();
                } else {
                    return false;
                }
            }
        }
        while (!leftStack.isEmpty()) {
            if (starStack.isEmpty() || leftStack.peek() > starStack.peek()) {
                return false;
            }
            leftStack.pop();
            starStack.pop();
        }
        return true;
    }

    /**
     * 808. 分汤
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 你有两种汤，A 和 B，每种初始为 n 毫升。在每一轮中，会随机选择以下四种服务操作中的一种，每种操作的概率为 0.25，且与之前的所有轮次 无关：
     *
     * 从汤 A 取 100 毫升，从汤 B 取 0 毫升
     * 从汤 A 取 75 毫升，从汤 B 取 25 毫升
     * 从汤 A 取 50 毫升，从汤 B 取 50 毫升
     * 从汤 A 取 25 毫升，从汤 B 取 75 毫升
     * 注意：
     *
     * 不存在先分配 100 ml 汤B 的操作。
     * 汤 A 和 B 在每次操作中同时被倒入。
     * 如果一次操作要求你倒出比剩余的汤更多的量，请倒出该汤剩余的所有部分。
     * 操作过程在任何回合中任一汤被用完后立即停止。
     *
     * 返回汤 A 在 B 前耗尽的概率，加上两种汤在 同一回合 耗尽概率的一半。返回值在正确答案 10-5 的范围内将被认为是正确的。
     *
     *
     *
     * 示例 1:
     *
     * 输入：n = 50
     * 输出：0.62500
     * 解释：
     * 如果我们选择前两个操作，A 首先将变为空。
     * 对于第三个操作，A 和 B 会同时变为空。
     * 对于第四个操作，B 首先将变为空。
     * 所以 A 变为空的总概率加上 A 和 B 同时变为空的概率的一半是 0.25 *(1 + 1 + 0.5 + 0)= 0.625。
     * 示例 2:
     *
     * 输入：n = 100
     * 输出：0.71875
     * 解释：
     * 如果我们选择第一个操作，A 首先将变为空。
     * 如果我们选择第二个操作，A 将在执行操作 [1, 2, 3] 时变为空，然后 A 和 B 在执行操作 4 时同时变空。
     * 如果我们选择第三个操作，A 将在执行操作 [1, 2] 时变为空，然后 A 和 B 在执行操作 3 时同时变空。
     * 如果我们选择第四个操作，A 将在执行操作 1 时变为空，然后 A 和 B 在执行操作 2 时同时变空。
     * 所以 A 变为空的总概率加上 A 和 B 同时变为空的概率的一半是 0.71875。
     *
     *
     * 提示:
     *
     * 0 <= n <= 109
     */
    public double soupServings(int n) {
        n = (int) Math.ceil(n / 25.0);
        if (n >= 179) {
            return 1.0;
        }
        double[][] memo = new double[n + 1][n + 1];
        return dfs2(n, n, memo);
    }

    public double dfs2(int a, int b, double[][] memo) {
        if (a <= 0 && b <= 0) {
            return 0.5;
        } else if (a <= 0) {
            return 1;
        } else if (b <= 0) {
            return 0;
        }
        if (memo[a][b] == 0) {
            memo[a][b] = 0.25 * (dfs2(a - 4, b, memo) + dfs2(a - 3, b - 1, memo)
                    + dfs2(a - 2, b - 2, memo) + dfs2(a - 1, b - 3, memo));
        }
        return memo[a][b];
    }

    /**
     * 1840. 最高建筑高度
     * 困难
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 在一座城市里，你需要建 n 栋新的建筑。这些新的建筑会从 1 到 n 编号排成一列。
     *
     * 这座城市对这些新建筑有一些规定：
     *
     * 每栋建筑的高度必须是一个非负整数。
     * 第一栋建筑的高度 必须 是 0 。
     * 任意两栋相邻建筑的高度差 不能超过  1 。
     * 除此以外，某些建筑还有额外的最高高度限制。这些限制会以二维整数数组 restrictions 的形式给出，
     * 其中 restrictions[i] = [idi, maxHeighti] ，表示建筑 idi 的高度 不能超过 maxHeighti 。
     *
     * 题目保证每栋建筑在 restrictions 中 至多出现一次 ，同时建筑 1 不会 出现在 restrictions 中。
     *
     * 请你返回 最高 建筑能达到的 最高高度 。
     *
     *
     *
     * 示例 1：
     *
     *
     * 输入：n = 5, restrictions = [[2,1],[4,1]]
     * 输出：2
     * 解释：上图中的绿色区域为每栋建筑被允许的最高高度。
     * 我们可以使建筑高度分别为 [0,1,2,1,2] ，最高建筑的高度为 2 。
     * 示例 2：
     *
     *
     * 输入：n = 6, restrictions = []
     * 输出：5
     * 解释：上图中的绿色区域为每栋建筑被允许的最高高度。
     * 我们可以使建筑高度分别为 [0,1,2,3,4,5] ，最高建筑的高度为 5 。
     * 示例 3：
     *
     *
     * 输入：n = 10, restrictions = [[5,3],[2,5],[7,4],[10,3]]
     * 输出：5
     * 解释：上图中的绿色区域为每栋建筑被允许的最高高度。
     * 我们可以使建筑高度分别为 [0,1,2,3,3,4,4,5,4,3] ，最高建筑的高度为 5 。
     *
     *
     * 提示：
     *
     * 2 <= n <= 109
     * 0 <= restrictions.length <= min(n - 1, 105)
     * 2 <= idi <= n
     * idi 是 唯一的 。
     * 0 <= maxHeighti <= 109
     */
    public static int maxBuilding(int n, int[][] restrictions) {
        // 相邻建筑高度差<=1，则受限制高度具有传递性,受限数组中还应加入 [1,0]和[n,n-1]
        // 设建筑i的真实受限高度为l(i)
        // 要求得建筑高度的最大值，该值一定出现在两相邻真实受限建筑之间
        // 设相邻真实受限建筑i,j之间的最大高度为f,则有f(ij)-l(i) + f(ij)-l(j) <= j-i, f(ij)max = (j-i+l(i)+l(j)) / 2
        if (restrictions.length == 0) {
            return n - 1;
        }
        // 按建筑编号排序
        Arrays.sort(restrictions, Comparator.comparingInt(a -> a[0]));
        int[][] rs = new int[restrictions.length + 2][2];
        System.arraycopy(restrictions, 0, rs, 1, restrictions.length);
        int m = rs.length;
        rs[0][0] = 1;
        rs[0][1] = 0;
        rs[m - 1][0] = n;
        rs[m - 1][1] = n - 1;
        // 从左到右，从右到左维护真实受限高度
        for (int i = 1; i < m; i++) {
            rs[i][1] = Math.min(rs[i][1], rs[i - 1][1] + rs[i][0] - rs[i - 1][0]);
        }
        for (int i = m - 2; i >= 0; i--) {
            rs[i][1] = Math.min(rs[i][1], rs[i + 1][1] + rs[i + 1][0] - rs[i][0]);
        }
        int ans = 0;
        for (int i = 1; i < m; i++) {
            // f(ij)-l(i) + f(ij)-l(j) <= j-i, f(ij)max = (j-i+l(i)+l(j)) / 2
            ans = Math.max(ans, (rs[i][0] - rs[i - 1][0] + rs[i][1] + rs[i - 1][1]) / 2);
        }
        return ans;
    }

    /**
     * 264. 丑数 II
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
     *
     * 丑数 就是质因子只包含 2、3 和 5 的正整数。
     *
     *
     *
     * 示例 1：
     *
     * 输入：n = 10
     * 输出：12
     * 解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
     * 示例 2：
     *
     * 输入：n = 1
     * 输出：1
     * 解释：1 通常被视为丑数。
     *
     *
     * 提示：
     *
     * 1 <= n <= 1690
     */
    public int nthUglyNumber(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        int p2 = 1, p3 = 1, p5 = 1;
        for (int i = 2; i <= n; i++) {
            int num2 = dp[p2] * 2, num3 = dp[p3] * 3, num5 = dp[p5] * 5;
            dp[i] = Math.min(Math.min(num2, num3), num5);
            if (dp[i] == num2) {
                p2++;
            }
            if (dp[i] == num3) {
                p3++;
            }
            if (dp[i] == num5) {
                p5++;
            }
        }
        return dp[n];
    }

    /**
     * 2840. 判断通过操作能否让字符串相等 II
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你两个字符串 s1 和 s2 ，两个字符串长度都为 n ，且只包含 小写 英文字母。
     *
     * 你可以对两个字符串中的 任意一个 执行以下操作 任意 次：
     *
     * 选择两个下标 i 和 j ，满足 i < j 且 j - i 是 偶数，然后 交换 这个字符串中两个下标对应的字符。
     *
     *
     * 如果你可以让字符串 s1 和 s2 相等，那么返回 true ，否则返回 false 。
     *
     *
     *
     *
     *
     * 示例 1：
     *
     * 输入：s1 = "abcdba", s2 = "cabdab"
     * 输出：true
     * 解释：我们可以对 s1 执行以下操作：
     * - 选择下标 i = 0 ，j = 2 ，得到字符串 s1 = "cbadba" 。
     * - 选择下标 i = 2 ，j = 4 ，得到字符串 s1 = "cbbdaa" 。
     * - 选择下标 i = 1 ，j = 5 ，得到字符串 s1 = "cabdab" = s2 。
     * 示例 2：
     *
     * 输入：s1 = "abe", s2 = "bea"
     * 输出：false
     * 解释：无法让两个字符串相等。
     *
     *
     * 提示：
     *
     * n == s1.length == s2.length
     * 1 <= n <= 105
     * s1 和 s2 只包含小写英文字母。
     */
    public boolean checkStrings(String s1, String s2) {
        // 下标奇数位之间可以无限互换，偶数位亦然，所以只需要奇偶位上分别相等（元素相等，个数相等）即可
        if (s1.length() != s2.length()) {
            return false;
        }
        int n = s1.length();
        int[] odds = new int[26], evens = new int[26];
        boolean oddBit = false;
        for (int i = 0; i < n; i++) {
            int[] cnts = oddBit ? odds : evens;
            cnts[s1.charAt(i) - 'a']++;
            oddBit = !oddBit;
        }
        oddBit = false;
        for (int i = 0; i < n; i++) {
            int[] cnts = oddBit ? odds : evens;
            if (--cnts[s2.charAt(i) - 'a'] < 0) {
                return false;
            }
            oddBit = !oddBit;
        }
        for (int odd : odds) {
            if (odd > 0) {
                return false;
            }
        }
        for (int even : evens) {
            if (even > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 3324. 出现在屏幕上的字符串序列
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个字符串 target。
     *
     * Alice 将会使用一种特殊的键盘在她的电脑上输入 target，这个键盘 只有两个 按键：
     *
     * 按键 1：在屏幕上的字符串后追加字符 'a'。
     * 按键 2：将屏幕上字符串的 最后一个 字符更改为英文字母表中的 下一个 字符。例如，'c' 变为 'd'，'z' 变为 'a'。
     * 注意，最初屏幕上是一个空字符串 ""，所以她 只能 按按键 1。
     *
     * 请你考虑按键次数 最少 的情况，按字符串出现顺序，返回 Alice 输入 target 时屏幕上出现的所有字符串列表。
     *
     *
     *
     * 示例 1：
     *
     * 输入： target = "abc"
     *
     * 输出： ["a","aa","ab","aba","abb","abc"]
     *
     * 解释：
     *
     * Alice 按键的顺序如下：
     *
     * 按下按键 1，屏幕上的字符串变为 "a"。
     * 按下按键 1，屏幕上的字符串变为 "aa"。
     * 按下按键 2，屏幕上的字符串变为 "ab"。
     * 按下按键 1，屏幕上的字符串变为 "aba"。
     * 按下按键 2，屏幕上的字符串变为 "abb"。
     * 按下按键 2，屏幕上的字符串变为 "abc"。
     * 示例 2：
     *
     * 输入： target = "he"
     *
     * 输出： ["a","b","c","d","e","f","g","h","ha","hb","hc","hd","he"]
     *
     *
     *
     * 提示：
     *
     * 1 <= target.length <= 400
     * target 仅由小写英文字母组成。
     */
    public static List<String> stringSequence(String target) {
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int n = target.length();
        for (int i = 0; i < n; i++) {
            char c = target.charAt(i);
            for (char j = 'a'; j <= c; j++) {
                sb.append(j);
                ans.add(sb.toString());
                if (j < c) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        return ans;
    }

    /**
     * 956. 最高的广告牌
     * 困难
     * 相关标签
     * premium lock icon
     * 相关企业
     * 你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。
     *
     * 你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。
     *
     * 返回 广告牌的最大可能安装高度 。如果没法安装广告牌，请返回 0 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：[1,2,3,6]
     * 输出：6
     * 解释：我们有两个不相交的子集 {1,2,3} 和 {6}，它们具有相同的和 sum = 6。
     * 示例 2：
     *
     * 输入：[1,2,3,4,5,6]
     * 输出：10
     * 解释：我们有两个不相交的子集 {2,3,5} 和 {4,6}，它们具有相同的和 sum = 10。
     * 示例 3：
     *
     * 输入：[1,2]
     * 输出：0
     * 解释：没法安装广告牌，所以返回 0。
     *
     *
     * 提示：
     *
     * 0 <= rods.length <= 20
     * 1 <= rods[i] <= 1000
     * sum(rods[i]) <= 5000
     */
    public int tallestBillboard(int[] rods) {
        // TODO 动态规划
        return 0;
        // 1 2 3 4
    }

    /**
     * 2438. 二的幂数组中查询范围内的乘积
     * 中等
     * 相关标签
     * premium lock icon
     * 相关企业
     * 提示
     * 给你一个正整数 n ，你需要找到一个下标从 0 开始的数组 powers ，它包含 最少 数目的 2 的幂，且它们的和为 n 。powers 数组是 非递减 顺序的。
     * 根据前面描述，构造 powers 数组的方法是唯一的。
     *
     * 同时给你一个下标从 0 开始的二维整数数组 queries ，其中 queries[i] = [lefti, righti] ，
     * 其中 queries[i] 表示请你求出满足 lefti <= j <= righti 的所有 powers[j] 的乘积。
     *
     * 请你返回一个数组 answers ，长度与 queries 的长度相同，其中 answers[i]是第 i 个查询的答案。
     * 由于查询的结果可能非常大，请你将每个 answers[i] 都对 109 + 7 取余 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：n = 15, queries = [[0,1],[2,2],[0,3]]
     * 输出：[2,4,64]
     * 解释：
     * 对于 n = 15 ，得到 powers = [1,2,4,8] 。没法得到元素数目更少的数组。
     * 第 1 个查询的答案：powers[0] * powers[1] = 1 * 2 = 2 。
     * 第 2 个查询的答案：powers[2] = 4 。
     * 第 3 个查询的答案：powers[0] * powers[1] * powers[2] * powers[3] = 1 * 2 * 4 * 8 = 64 。
     * 每个答案对 109 + 7 得到的结果都相同，所以返回 [2,4,64] 。
     * 示例 2：
     *
     * 输入：n = 2, queries = [[0,0]]
     * 输出：[2]
     * 解释：
     * 对于 n = 2, powers = [2] 。
     * 唯一一个查询的答案是 powers[0] = 2 。答案对 109 + 7 取余后结果相同，所以返回 [2] 。
     *
     *
     * 提示：
     *
     * 1 <= n <= 109
     * 1 <= queries.length <= 105
     * 0 <= starti <= endi < powers.length
     */
    public static int[] productQueries(int n, int[][] queries) {
        int[] powers = new int[Integer.bitCount(n)];
        int idx = 0, pow = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                powers[idx++] = pow;
            }
            n >>= 1;
            pow <<= 1;
        }
        int m = queries.length, MOD = 1000000007;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            long mul = powers[queries[i][0]];
            for (int j = queries[i][0] + 1; j <= queries[i][1]; j++) {
                mul *= powers[j];
                mul %= MOD;
            }
            ans[i] = (int) mul;
        }
        return ans;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[][] nums = {{0,1},{2,2},{0,3}};
        System.out.println(Arrays.toString(productQueries(15, nums)));
    }
}
