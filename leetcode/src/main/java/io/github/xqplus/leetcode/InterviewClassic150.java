package io.github.xqplus.leetcode;

import java.util.*;

public class InterviewClassic150 {

    /**
     * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。<p>
     * 输入：a = "1010", b = "1011"<br>
     * 输出："10101"
     */
    public static String addBinary(String a, String b) {
        String result = "";
        int indexA = a.length() - 1, indexB = b.length() - 1;
        int carry = 0;
        while (indexA >= 0 || indexB >= 0) {
            int bitA = indexA >= 0 ? a.charAt(indexA) - 48 : 0;
            int bitB = indexB >= 0 ? b.charAt(indexB) - 48 : 0;
            int add = bitA + bitB + carry;
            if (add >= 2) {
                add %= 2;
                carry = 1;
            } else {
                carry = 0;
            }
            result = add + result;
            indexA--;
            indexB--;
        }
        if (carry != 0) {
            result = carry + result;
        }
        return result;
    }

    /**
     * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。<br>
     * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。<br>
     * 如果存在，返回 true ；否则，返回 false 。<p>
     * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
     * 输出：true
     */
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) { // 叶子节点
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。<br>
     * 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。<p>
     * 输入：head = [1,2,3,4,5], left = 2, right = 4<br>
     * 输出：[1,4,3,2,5]<p>
     * 输入：head = [5], left = 1, right = 1<br>
     * 输出：[5]<p>
     * 链表中节点数目为 n<br>
     * 1 <= n <= 500<br>
     * -500 <= Node.val <= 500<br>
     * 1 <= left <= right <= n<p>
     * 进阶： 你可以使用一趟扫描完成反转吗？
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode help = new ListNode();
        help.next = head;
        // 找到left前一个节点pre
        ListNode pre = help;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next; // left节点
        ListNode next;
        for (int i = 0; i < right - left; i++) {
            // 将left后的节点依次往前提
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return help.next;
    }

    /**
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * 输入：l1 = [1,2,4], l2 = [1,3,4]
     * 输出：[1,1,2,3,4,4]
     * 输入：l1 = [], l2 = []
     * 输出：[]
     * 输入：l1 = [], l2 = [0]
     * 输出：[0]
     * 两个链表的节点数目范围是 [0, 50]
     * -100 <= Node.val <= 100
     * l1 和 l2 均按 非递减顺序 排列
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode();
        ListNode h = head;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                h.next = list1;
                list1 = list1.next;
            } else {
                h.next = list2;
                list2 = list2.next;
            }
            h = h.next;
        }
        h.next = list1 != null ? list1 : list2;
        return head.next;
    }

    /**
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * <p>
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     * <p>
     * |A|B|C|E|
     * |S|F|C|S|
     * |A|D|E|E|
     * <p>
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board 和 word 仅由大小写英文字母组成
     */
    public static boolean exist(char[][] board, String word) {
        // 预剪枝处理
        byte[] boardHelp = new byte[58];
        for (char[] cs : board) {
            for (char c : cs) {
                boardHelp[c - 65]++;
            }
        }
        for (char c : word.toCharArray()) {
            if (boardHelp[c - 65] == 0) {
                return false;
            } else {
                boardHelp[c - 65]--;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (dfs(board, i, j, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean dfs(char[][] board, int i, int j, String word, int index) {
        // 字符串匹配成功
        if (index == word.length()) {
            return true;
        }
        // 越界或者字符串不匹配
        if (i < 0 || i >= board.length || j < 0 || j >= board[i].length || board[i][j] != word.charAt(index)) {
            return false;
        }
        // 标记已访问
        char tmp = board[i][j];
        board[i][j] = '#';

        boolean res = dfs(board, i + 1, j, word, index + 1)
                || dfs(board, i - 1, j, word, index + 1)
                || dfs(board, i, j + 1, word, index + 1)
                || dfs(board, i, j - 1, word, index + 1);
        board[i][j] = tmp;
        return res;
    }

    /**
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     * 输入：root = [1,2,2,3,4,4,3]
     * 输出：true
     * 输入：root = [1,2,2,null,3,null,3]
     * 输出：false
     * 树中节点数目在范围 [1, 1000] 内
     * -100 <= Node.val <= 100
     * 进阶：你可以运用递归和迭代两种方法解决这个问题吗？
     */
    public static boolean isSymmetric(TreeNode root) {
        // [0,0,0,null,0,null,0]
        if (root.left == null && root.right == null) {
            return true;
        }
        StringBuilder sbLeft = new StringBuilder();
        search(root.left, sbLeft, true);
        StringBuilder sbRight = new StringBuilder();
        search(root.right, sbRight, false);
        return sbLeft.toString().equals(sbRight.toString());
    }

    public static void search(TreeNode node, StringBuilder sb, boolean leftSearch) {
        if (node == null) {
            sb.append(101);
            return;
        }
        sb.append(node.val);
        if (leftSearch) {
            search(node.left, sb, true);
            search(node.right, sb, true);
        } else {
            search(node.right, sb, false);
            search(node.left, sb, false);
        }
    }

    /**
     * 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
     * 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
     * class Node {
     * public int val;
     * public List<Node> neighbors;
     * }
     * 测试用例格式：
     * 简单起见，每个节点的值都和它的索引相同。例如，第一个节点值为 1（val = 1），第二个节点值为 2（val = 2），以此类推。该图在测试用例中使用邻接列表表示。
     * 邻接列表 是用于表示有限图的无序列表的集合。每个列表都描述了图中节点的邻居集。
     * 给定节点将始终是图中的第一个节点（值为 1）。你必须将 给定节点的拷贝 作为对克隆图的引用返回。
     * 示例 1：
     * 输入：adjList = [[2,4],[1,3],[2,4],[1,3]]
     * 输出：[[2,4],[1,3],[2,4],[1,3]]
     * 解释：
     * 图中有 4 个节点。
     * 节点 1 的值是 1，它有两个邻居：节点 2 和 4 。
     * 节点 2 的值是 2，它有两个邻居：节点 1 和 3 。
     * 节点 3 的值是 3，它有两个邻居：节点 2 和 4 。
     * 节点 4 的值是 4，它有两个邻居：节点 1 和 3 。
     * 示例 2：
     * 输入：adjList = [[]]
     * 输出：[[]]
     * 解释：输入包含一个空列表。该图仅仅只有一个值为 1 的节点，它没有任何邻居。
     * 示例 3：
     * 输入：adjList = []
     * 输出：[]
     * 解释：这个图是空的，它不含任何节点。
     * 提示：
     * 这张图中的节点数在 [0, 100] 之间。
     * 1 <= Node.val <= 100
     * 每个节点值 Node.val 都是唯一的，
     * 图中没有重复的边，也没有自环。
     * 图是连通图，你可以从给定节点访问到所有节点。
     */
    private static Map<Node, Node> visited = new HashMap<>();

    public static Node cloneGraph(Node node) {
        if (node == null) return null;

        // 边界条件：搜索到已拷贝的节点
        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        // 深拷贝
        Node deepCloneNode = new Node(node.val);
        visited.put(node, deepCloneNode);

        for (Node neighbor : node.neighbors) {
            deepCloneNode.neighbors.add(cloneGraph(neighbor));
        }

        return deepCloneNode;
    }

    /**
     * <2>
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * 示例 1:
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     * 示例 2:
     * 输入：nums = [-1,-100,3,99], k = 2
     * 输出：[3,99,-1,-100]
     * 解释:
     * 向右轮转 1 步: [99,-1,-100,3]
     * 向右轮转 2 步: [3,99,-1,-100]
     * 提示：
     * 1 <= nums.length <= 105
     * -231 <= nums[i] <= 231 - 1
     * 0 <= k <= 105
     * 进阶：
     * 尽可能想出更多的解决方案，至少有 三种 不同的方法可以解决这个问题。
     * 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
     */
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        if (k > 0) {
            reverse(nums, 0, n - 1);
            reverse(nums, 0, k - 1);
            reverse(nums, k, n - 1);
        }
    }

    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
     * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
     * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
     * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
     * 示例 1：
     * 输入：candidates = [2,3,6,7], target = 7
     * 输出：[[2,2,3],[7]]
     * 解释：
     * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
     * 7 也是一个候选， 7 = 7 。
     * 仅有这两种组合。
     * 示例 2：
     * 输入: candidates = [2,3,5], target = 8
     * 输出: [[2,2,2,2],[2,3,3],[3,5]]
     * 示例 3：
     * 输入: candidates = [2], target = 1
     * 输出: []
     * 提示：
     * 1 <= candidates.length <= 30
     * 2 <= candidates[i] <= 40
     * candidates 的所有元素 互不相同
     * 1 <= target <= 40
     */
//    public List<List<Integer>> combinationSum(int[] candidates, int target) {
//
//    }

    /**
     * <3>
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * 给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
     * 示例 1：
     * 输入：n = 4
     * 输出：2
     * 解释：如上图所示，4 皇后问题存在两个不同的解法。
     * 示例 2：
     * 输入：n = 1
     * 输出：1
     * 提示：
     * 1 <= n <= 9
     */
    public static int totalNQueens(int n) {
        return backtrack(n, 0, 0, 0, 0);
    }

    private static int backtrack(int n, int row, int cols, int diag1, int diag2) {
        // 终止条件：所有行已放置皇后
        if (row == n) {
            return 1;
        }

        int count = 0;
        // 计算当前行可用的列位置（二进制位为1表示可放置）
        int availablePositions = ((1 << n) - 1) & ~(cols | diag1 | diag2);

        // 遍历所有可用位置
        while (availablePositions != 0) {
            // 取最低位的1作为当前位置
            int position = availablePositions & -availablePositions;
            availablePositions ^= position; // 移除已选位置

            // 递归处理下一行，更新列和对角线掩码
            count += backtrack(n, row + 1,
                    cols | position,
                    (diag1 | position) << 1,
                    (diag2 | position) >> 1);
        }
        return count;
    }

    /**
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     * 示例 1：
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * 示例 2：
     * 输入：nums = [0,1]
     * 输出：[[0,1],[1,0]]
     * 示例 3：
     * 输入：nums = [1]
     * 输出：[[1]]
     * 提示：
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * nums 中的所有整数 互不相同
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, new ArrayList<>(), nums);
        return res;
    }

    private static void backtrack(List<List<Integer>> res, List<Integer> used, int[] nums) {
        // 边界条件
        if (used.size() == nums.length) {
            res.add(new ArrayList<>(used));
            return;
        }

        // 递归
        for (int num : nums) {
            if (used.contains(num)) {
                continue;
            }
            used.add(num);
            backtrack(res, used, nums);

            // 回溯
            used.remove(used.size() - 1);
        }
    }

    /**
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     * 示例 1：
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * 示例 2：
     * 输入：intervals = [[1,4],[4,5]]
     * 输出：[[1,5]]
     * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
     * 提示：
     * 1 <= intervals.length <= 104
     * intervals[i].length == 2
     * 0 <= starti <= endi <= 104
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] mergedLast = merged.get(merged.size() - 1);
            if (intervals[i][0] > mergedLast[1]) {
                merged.add(intervals[i]);
            } else {
                mergedLast[1] = Math.max(mergedLast[1], intervals[i][1]);
            }
        }
        return merged.toArray(new int[merged.size()][2]);
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
        System.out.println(addBinary("1010", "1011"));

        TreeNode root = new TreeNode(5);
        TreeNode node1 = new TreeNode(4);
        TreeNode node2 = new TreeNode(8);
        TreeNode node3 = new TreeNode(11);
        TreeNode node4 = new TreeNode(13);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(7);
        TreeNode node7 = new TreeNode(2);
        TreeNode node8 = new TreeNode(1);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        node5.right = node8;
        System.out.println(hasPathSum(root, 22));

        char[][] board = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        System.out.println(exist(board, "ABCCED"));

        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums));

        System.out.println(totalNQueens(4));

        int[] nums = {1, 2, 3};
        System.out.println(permute(nums));
         */

        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println(merge(intervals));
    }
}
