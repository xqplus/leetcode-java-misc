package io.github.xqplus.leetcode;

import java.util.*;

/**
 * 随机问题类一
 *
 * @author chenq
 * @since 2025.4.11
 */
public class RandomProblem1 {

    /**
     * LCR 150. 彩灯装饰记录 II
     * 简单
     * 一棵圣诞树记作根节点为 root 的二叉树，节点值为该位置装饰彩灯的颜色编号。
     * 请按照从左到右的顺序返回每一层彩灯编号，每一层的结果记录于一行。
     * 示例 1：
     * 输入：root = [8,17,21,18,null,null,6]
     * 输出：[[8],[17,21],[18,6]]
     * 提示：
     * 节点总数 <= 1000
     */
    public List<List<Integer>> decorateRecord(TreeNode root) {
        // 广度优先搜索二叉树
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            result.add(list);
        }
        return result;
    }

    /**
     * 2044. 统计按位或能得到最大值的子集数目
     * 中等 位运算 数组 回溯 枚举
     * 给你一个整数数组 nums ，请你找出 nums 子集 按位或 可能得到的 最大值 ，并返回按位或能得到最大值的 不同非空子集的数目 。
     * 如果数组 a 可以由数组 b 删除一些元素（或不删除）得到，则认为数组 a 是数组 b 的一个 子集 。如果选中的元素下标位置不一样，则认为两个子集 不同 。
     * 对数组 a 执行 按位或 ，结果等于 a[0] OR a[1] OR ... OR a[a.length - 1]（下标从 0 开始）。
     * 示例 1：
     * 输入：nums = [3,1]
     * 输出：2
     * 解释：子集按位或能得到的最大值是 3 。有 2 个子集按位或可以得到 3 ：
     * - [3]
     * - [3,1]
     * 示例 2：
     * 输入：nums = [2,2,2]
     * 输出：7
     * 解释：[2,2,2] 的所有非空子集的按位或都可以得到 2 。总共有 2^3 - 1 = 7 个子集。
     * 示例 3：
     * 输入：nums = [3,2,1,5]
     * 输出：6
     * 解释：子集按位或可能的最大值是 7 。有 6 个子集按位或可以得到 7 ：
     * - [3,5]
     * - [3,1,5]
     * - [3,2,5]
     * - [3,2,1,5]
     * - [2,5]
     * - [2,1,5]
     * 提示：
     * 1 <= nums.length <= 16
     * 1 <= nums[i] <= 10^5
     */
    static int max, count;

    public static int countMaxOrSubsets(int[] nums) {
        for (int num : nums) {
            max |= num;
        }
        dfs(nums, 0, 0);
        return count;
    }

    private static void dfs(int[] nums, int index, int orVal) {
        if (index == nums.length) {
            if (orVal == max) {
                count++;
            }
            return;
        }
        dfs(nums, index + 1, orVal | nums[index]);
        dfs(nums, index + 1, orVal);
    }

    /**
     * 814. 二叉树剪枝
     * 中等 树 深度优先搜索 二叉树
     * 给你二叉树的根结点 root ，此外树的每个结点的值要么是 0 ，要么是 1 。
     * 返回移除了所有不包含 1 的子树的原二叉树。
     * 节点 node 的子树为 node 本身加上所有 node 的后代。
     * 示例 1：
     * 输入：root = [1,null,0,0,1]
     * 输出：[1,null,0,null,1]
     * 解释：
     * 只有红色节点满足条件“所有不包含 1 的子树”。 右图为返回的答案。
     * 示例 2：
     * 输入：root = [1,0,1,0,0,0,1]
     * 输出：[1,null,1,null,1]
     * 示例 3：
     * 输入：root = [1,1,0,1,1,0,1,0]
     * 输出：[1,1,0,1,1,null,1]
     * 提示：
     * 树中节点的数目在范围 [1, 200] 内
     * Node.val 为 0 或 1
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null; // 叶子节点
        }
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);

        if (root.left == null && root.right == null && root.val == 0) {
            return null; // 满足剪枝条件
        }
        return root;
    }

    /**
     * 2855. 使数组成为递增数组的最少右移次数
     * 简单 数组
     * 给你一个长度为 n 下标从 0 开始的数组 nums ，数组中的元素为 互不相同 的正整数。
     * 请你返回让 nums 成为递增数组的 最少右移 次数，如果无法得到递增数组，返回 -1 。
     * 一次 右移 指的是同时对所有下标进行操作，将下标为 i 的元素移动到下标 (i + 1) % n 处。
     * 示例 1：
     * 输入：nums = [3,4,5,1,2]
     * 输出：2
     * 解释：
     * 第一次右移后，nums = [2,3,4,5,1] 。
     * 第二次右移后，nums = [1,2,3,4,5] 。
     * 现在 nums 是递增数组了，所以答案为 2 。
     * 示例 2：
     * 输入：nums = [1,3,5]
     * 输出：0
     * 解释：nums 已经是递增数组了，所以答案为 0 。
     * 示例 3：
     * 输入：nums = [2,1,4]
     * 输出：-1
     * 解释：无法将数组变为递增数组。
     * 提示：
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     * nums 中的整数互不相同。
     */
    public static int minimumRightShifts(List<Integer> nums) {
        // 局部递增，最多出现两个递增序列
        // 两个序列的情况下，后一个递增序列最大值必须小于前一个递增序列最小值，其实就是最后一个值要小于第一个值
        // 移动次数是第二个递增序列的数量
        if (nums.size() == 1) {
            return 0;
        }
        int count = 1, res = 0;
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < nums.get(i - 1)) {
                if (++count > 2) {
                    return -1;
                }
            }
            if (count == 2) {
                res++;
            }
        }
        return count == 2 && nums.get(nums.size() - 1) > nums.get(0) ? -1 : res;
    }

    /**
     * 2582. 递枕头
     * 简单 数学 模拟
     * n 个人站成一排，按从 1 到 n 编号。最初，排在队首的第一个人拿着一个枕头。每秒钟，拿着枕头的人会将枕头传递给队伍中的下一个人。
     * 一旦枕头到达队首或队尾，传递方向就会改变，队伍会继续沿相反方向传递枕头。
     * 例如，当枕头到达第 n 个人时，TA 会将枕头传递给第 n - 1 个人，然后传递给第 n - 2 个人，依此类推。
     * 给你两个正整数 n 和 time ，返回 time 秒后拿着枕头的人的编号。
     * 示例 1：
     * 输入：n = 4, time = 5
     * 输出：2
     * 解释：队伍中枕头的传递情况为：1 -> 2 -> 3 -> 4 -> 3 -> 2 。
     * 5 秒后，枕头传递到第 2 个人手中。
     * 示例 2：
     * 输入：n = 3, time = 2
     * 输出：3
     * 解释：队伍中枕头的传递情况为：1 -> 2 -> 3 。
     * 2 秒后，枕头传递到第 3 个人手中。
     * 提示：
     * 2 <= n <= 1000
     * 1 <= time <= 1000
     */
    public static int passThePillow(int n, int time) {
        // 1 2 3
        // 1-2 1/2=0 1%2=1
        // 1-2-3 2/2=1 2%2=0
        // 1-2-3-2 3/2=1 3%2=1
        // 1-2-3-2-1 4/2=2 4%2=0
        // 1-2-3-2-1-2 5/2=2 5%2=1
        // 1-2-3-2-1-2-3 6/2=3 6%2=0
        // 1-2-3-2-1-2-3-2 7/2=3 7%2=1
        return (time / (n - 1)) % 2 == 0 ? (time % (n - 1)) + 1 : n - (time % (n - 1));
    }

    /**
     * 2181. 合并零之间的节点
     * 中等 链表 模拟
     * 给你一个链表的头节点 head ，该链表包含由 0 分隔开的一连串整数。链表的 开端 和 末尾 的节点都满足 Node.val == 0 。
     * 对于每两个相邻的 0 ，请你将它们之间的所有节点合并成一个节点，其值是所有已合并节点的值之和。然后将所有 0 移除，修改后的链表不应该含有任何 0 。
     * 返回修改后链表的头节点 head 。
     * 示例 1：
     * 输入：head = [0,3,1,0,4,5,2,0]
     * 输出：[4,11]
     * 解释：
     * 上图表示输入的链表。修改后的链表包含：
     * - 标记为绿色的节点之和：3 + 1 = 4
     * - 标记为红色的节点之和：4 + 5 + 2 = 11
     * 示例 2：
     * 输入：head = [0,1,0,3,0,2,2,0]
     * 输出：[1,3,4]
     * 解释：
     * 上图表示输入的链表。修改后的链表包含：
     * - 标记为绿色的节点之和：1 = 1
     * - 标记为红色的节点之和：3 = 3
     * - 标记为黄色的节点之和：2 + 2 = 4
     * 提示：
     * 列表中的节点数目在范围 [3, 2 * 10^5] 内
     * 0 <= Node.val <= 1000
     * 不 存在连续两个 Node.val == 0 的节点
     * 链表的 开端 和 末尾 节点都满足 Node.val == 0
     */
    public ListNode mergeNodes(ListNode head) {
        ListNode dummyHead = new ListNode();
        ListNode tail = dummyHead;
        int sum = 0;
        head = head.next;
        while (head != null) {
            if (head.val == 0) {
                tail.next = new ListNode(sum);
                tail = tail.next;
                sum = 0;
            } else {
                sum += head.val;
            }
            head = head.next;
        }
        return dummyHead.next;
    }

    /**
     * LCR 162. 数字 1 的个数
     * 困难 递归 数学 动态规划
     * 给定一个整数 num，计算所有小于等于 num 的非负整数中数字 1 出现的个数。
     * 示例 1：
     * 输入：num = 0
     * 输出：0
     * 示例 2：
     * 输入：num = 13
     * 输出：6
     * 提示：
     * 0 <= num < 10^9
     */
    public static int digitOneInNumber(int num) {
        int digit = 1, ans = 0;
        while (digit <= num) {
            int high = num / (digit * 10);
            int cur = num / digit % 10;
            int low = num % digit;
            if (cur > 1) {
                ans += (high + 1) * digit;
            } else if (cur == 1) {
                ans += high * digit + low + 1;
            } else {
                ans += high * digit;
            }
            digit *= 10;
        }
        return ans;
    }

    /**
     * 3069. 将元素分配到两个数组中 I
     * 简单 数组 模拟
     * 给你一个下标从 1 开始、包含 不同 整数的数组 nums ，数组长度为 n 。
     * 你需要通过 n 次操作，将 nums 中的所有元素分配到两个数组 arr1 和 arr2 中。
     * 在第一次操作中，将 nums[1] 追加到 arr1 。
     * 在第二次操作中，将 nums[2] 追加到 arr2 。
     * 之后，在第 i 次操作中：
     * 如果 arr1 的最后一个元素 大于 arr2 的最后一个元素，就将 nums[i] 追加到 arr1 。否则，将 nums[i] 追加到 arr2 。
     * 通过连接数组 arr1 和 arr2 形成数组 result 。例如，如果 arr1 == [1,2,3] 且 arr2 == [4,5,6] ，那么 result = [1,2,3,4,5,6] 。
     * 返回数组 result 。
     * 示例 1：
     * 输入：nums = [2,1,3]
     * 输出：[2,3,1]
     * 解释：在前两次操作后，arr1 = [2] ，arr2 = [1] 。
     * 在第 3 次操作中，由于 arr1 的最后一个元素大于 arr2 的最后一个元素（2 > 1），将 nums[3] 追加到 arr1 。
     * 3 次操作后，arr1 = [2,3] ，arr2 = [1] 。
     * 因此，连接形成的数组 result 是 [2,3,1] 。
     * 示例 2：
     * 输入：nums = [5,4,3,8]
     * 输出：[5,3,4,8]
     * 解释：在前两次操作后，arr1 = [5] ，arr2 = [4] 。
     * 在第 3 次操作中，由于 arr1 的最后一个元素大于 arr2 的最后一个元素（5 > 4），将 nums[3] 追加到 arr1 ，因此 arr1 变为 [5,3] 。
     * 在第 4 次操作中，由于 arr2 的最后一个元素大于 arr1 的最后一个元素（4 > 3），将 nums[4] 追加到 arr2 ，因此 arr2 变为 [4,8] 。
     * 4 次操作后，arr1 = [5,3] ，arr2 = [4,8] 。
     * 因此，连接形成的数组 result 是 [5,3,4,8] 。
     * 提示：
     * 3 <= n <= 50
     * 1 <= nums[i] <= 100
     * nums中的所有元素都互不相同。
     */
    public static int[] resultArray(int[] nums) {
//        int[] arr1 = new int[nums.length];
        int[] arr2 = new int[nums.length];
        int idx1 = 0, idx2 = 0;
//        arr1[idx1] = nums[0];
        arr2[idx2] = nums[1];
        for (int i = 2; i < nums.length; i++) {
            if (nums[idx1] > arr2[idx2]) {
                nums[++idx1] = nums[i];
            } else {
                arr2[++idx2] = nums[i];
            }
        }
        for (int i = 0; i <= idx2; i++) {
            nums[++idx1] = arr2[i];
        }
        return nums;
    }

    /**
     * 1046. 最后一块石头的重量
     * 简单 数组 堆（优先队列）
     * 有一堆石头，每块石头的重量都是正整数。
     * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
     * 示例：
     * 输入：[2,7,4,1,8,1]
     * 输出：1
     * 解释：
     * 先选出 7 和 8，得到 1，所以数组转换为 [2,4,1,1,1]，
     * 再选出 2 和 4，得到 2，所以数组转换为 [2,1,1,1]，
     * 接着是 2 和 1，得到 1，所以数组转换为 [1,1,1]，
     * 最后选出 1 和 1，得到 0，最终数组转换为 [1]，这就是最后剩下那块石头的重量。
     * 提示：
     * 1 <= stones.length <= 30
     * 1 <= stones[i] <= 1000
     */
    public int lastStoneWeight(int[] stones) {
        if (stones.length == 1) {
            return stones[0];
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        for (int stone : stones) {
            maxHeap.offer(stone);
        }
        while (maxHeap.size() > 1) {
            int y = maxHeap.poll();
            int x = maxHeap.poll();
            if (y > x) {
                maxHeap.offer(y - x);
            }
        }
        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    /**
     * 1232. 缀点成线
     * 简单 几何 数组 数学
     * 给定一个数组 coordinates ，其中 coordinates[i] = [x, y] ， [x, y] 表示横坐标为 x、纵坐标为 y 的点。
     * 请你来判断，这些点是否在该坐标系中属于同一条直线上。
     * 示例 1：
     * 输入：coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]
     * 输出：true
     * 示例 2：
     * 输入：coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]
     * 输出：false
     * 提示：
     * 2 <= coordinates.length <= 1000
     * coordinates[i].length == 2
     * -10^4 <= coordinates[i][0], coordinates[i][1] <= 10^4
     * coordinates 中不含重复的点
     */
    public boolean checkStraightLine(int[][] coordinates) {
        if (coordinates.length == 2) {
            return true;
        }
        int a1 = coordinates[1][0] - coordinates[0][0];
        int a2 = coordinates[1][1] - coordinates[0][1];
        for (int i = 2; i < coordinates.length; i++) {
            int b1 = coordinates[i][0] - coordinates[0][0];
            int b2 = coordinates[i][1] - coordinates[0][1];
            if (a1 * b2 != a2 * b1) { // 向量叉乘法 a1b2 - a2b1 = 0
                return false;
            }
        }
        return true;
    }

    /**
     * 1578. 使绳子变成彩色的最短时间
     * 中等 贪心 数字 字符串 动态规划
     * Alice 把 n 个气球排列在一根绳子上。给你一个下标从 0 开始的字符串 colors ，其中 colors[i] 是第 i 个气球的颜色。
     * Alice 想要把绳子装扮成 五颜六色的 ，且她不希望两个连续的气球涂着相同的颜色，所以她喊来 Bob 帮忙。
     * Bob 可以从绳子上移除一些气球使绳子变成 彩色 。给你一个 下标从 0 开始 的整数数组 neededTime ，
     * 其中 neededTime[i] 是 Bob 从绳子上移除第 i 个气球需要的时间（以秒为单位）。
     * 返回 Bob 使绳子变成 彩色 需要的 最少时间 。
     * 示例 1：
     * 输入：colors = "abaac", neededTime = [1,2,3,4,5]
     * 输出：3
     * 解释：在上图中，'a' 是蓝色，'b' 是红色且 'c' 是绿色。
     * Bob 可以移除下标 2 的蓝色气球。这将花费 3 秒。
     * 移除后，不存在两个连续的气球涂着相同的颜色。总时间 = 3 。
     * 示例 2：
     * 输入：colors = "abc", neededTime = [1,2,3]
     * 输出：0
     * 解释：绳子已经是彩色的，Bob 不需要从绳子上移除任何气球。
     * 示例 3：
     * 输入：colors = "aabaa", neededTime = [1,2,3,4,1]
     * 输出：2
     * 解释：Bob 会移除下标 0 和下标 4 处的气球。这两个气球各需要 1 秒来移除。
     * 移除后，不存在两个连续的气球涂着相同的颜色。总时间 = 1 + 1 = 2 。
     * 提示：
     * n == colors.length == neededTime.length
     * 1 <= n <= 105
     * 1 <= neededTime[i] <= 104
     * colors 仅由小写英文字母组成
     */
    public static int minCost(String colors, int[] neededTime) {
        int n = colors.length();
        if (n == 1) {
            return 0;
        }
        int i = 0, ans = 0;
        while (i < n) {
            char c = colors.charAt(i);
            int sum = 0, max = 0;
            while (i < n && colors.charAt(i) == c) {
                sum += neededTime[i];
                max = Math.max(max, neededTime[i]);
                i++;
            }
            ans += sum - max;
        }
        return ans;
    }

    /**
     * 1154. 一年中的第几天
     * 简单 数学 字符串
     * 给你一个字符串 date ，按 YYYY-MM-DD 格式表示一个 现行公元纪年法 日期。返回该日期是当年的第几天。
     * 示例 1：
     * 输入：date = "2019-01-09"
     * 输出：9
     * 解释：给定日期是2019年的第九天。
     * 示例 2：
     * 输入：date = "2019-02-10"
     * 输出：41
     * 提示：
     * date.length == 10
     * date[4] == date[7] == '-'，其他的 date[i] 都是数字
     * date 表示的范围从 1900 年 1 月 1 日至 2019 年 12 月 31 日
     */
    public int dayOfYear(String date) {
        int[] helper = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
        String[] split = date.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        return helper[month - 1] + day + (month > 2 && isLeap(year) ? 1 : 0);
    }

    private boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 1370. 上升下降字符串
     * 简单 哈希表 字符串 计数
     * 给你一个字符串 s ，请你根据下面的算法重新构造字符串：
     * 1. 从 s 中选出 最小 的字符，将它 接在 结果字符串的后面。
     * 2. 从 s 剩余字符中选出比上一个添加字符更大的 最小 字符，将它 接在 结果字符串后面。
     * 3. 重复步骤 2 ，直到你没法从 s 中选择字符。
     * 4. 从 s 中选出 最大 的字符，将它 接在 结果字符串的后面。
     * 5. 从 s 剩余字符中选出比上一个添加字符更小的 最大 字符，将它 接在 结果字符串后面。
     * 6. 重复步骤 5 ，直到你没法从 s 中选择字符。
     * 7. 重复步骤 1 到 6 ，直到 s 中所有字符都已经被选过。
     * 在任何一步中，如果最小或者最大字符不止一个 ，你可以选择其中任意一个，并将其添加到结果字符串。
     * 请你返回将 s 中字符重新排序后的 结果字符串 。
     * 示例 1：
     * 输入：s = "aaaabbbbcccc"
     * 输出："abccbaabccba"
     * 解释：第一轮的步骤 1，2，3 后，结果字符串为 result = "abc"
     * 第一轮的步骤 4，5，6 后，结果字符串为 result = "abccba"
     * 第一轮结束，现在 s = "aabbcc" ，我们再次回到步骤 1
     * 第二轮的步骤 1，2，3 后，结果字符串为 result = "abccbaabc"
     * 第二轮的步骤 4，5，6 后，结果字符串为 result = "abccbaabccba"
     * 示例 2：
     * 输入：s = "rat"
     * 输出："art"
     * 解释：单词 "rat" 在上述算法重排序以后变成 "art"
     * 提示：
     * 1 <= s.length <= 500
     * s 只包含小写英文字母。
     */
    public String sortString(String s) {
        int n = s.length();
        if (n == 1) {
            return s;
        }
        int[] buckets = new int[26];
        for (int i = 0; i < n; i++) {
            buckets[s.charAt(i) - 'a']++;
        }
        char[] chs = new char[n];
        int idx = 0;
        while (idx < n) {
            // 往右遍历
            for (int i = 0; i < 26; i++) {
                if (buckets[i] > 0) {
                    chs[idx++] = (char) (i + 'a');
                    buckets[i]--;
                }
            }
            // 往左遍历
            for (int i = 25; i >= 0; i--) {
                if (buckets[i] > 0) {
                    chs[idx++] = (char) (i + 'a');
                    buckets[i]--;
                }
            }
        }
        return new String(chs);
    }

    /**
     * 1673. 找出最具竞争力的子序列
     * 给你一个整数数组 nums 和一个正整数 k ，返回长度为 k 且最具 竞争力 的 nums 子序列。
     * 数组的子序列是从数组中删除一些元素（可能不删除元素）得到的序列。
     * 在子序列 a 和子序列 b 第一个不相同的位置上，如果 a 中的数字小于 b 中对应的数字，那么我们称子序列 a 比子序列 b（相同长度下）更具 竞争力 。
     * 例如，[1,3,4] 比 [1,3,5] 更具竞争力，在第一个不相同的位置，也就是最后一个位置上， 4 小于 5 。
     * 示例 1：
     * 输入：nums = [3,5,2,6], k = 2
     * 输出：[2,6]
     * 解释：在所有可能的子序列集合 {[3,5], [3,2], [3,6], [5,2], [5,6], [2,6]} 中，[2,6] 最具竞争力。
     * 示例 2：
     * 输入：nums = [2,4,3,3,5,4,9,6], k = 4
     * 输出：[2,3,3,4]
     * 提示：
     * 1 <= nums.length <= 105
     * 0 <= nums[i] <= 109
     * 1 <= k <= nums.length
     */
    public static int[] mostCompetitive(int[] nums, int k) {
        int n = nums.length;
        if (n == 1) {
            return nums;
        }

        int[] ans = new int[k];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            while (idx > 0 && n - i + idx > k && ans[idx - 1] > nums[i]) {
                idx--;
            }
            if (idx < k) {
                ans[idx++] = nums[i];
            }
        }
        return ans;
    }

    /**
     * 3180. 执行操作可获得的最大总奖励 I
     * 中等 数组 动态规划
     * 给你一个整数数组 rewardValues，长度为 n，代表奖励的值。
     * 最初，你的总奖励 x 为 0，所有下标都是 未标记 的。你可以执行以下操作 任意次 ：
     * 从区间 [0, n - 1] 中选择一个 未标记 的下标 i。
     * 如果 rewardValues[i] 大于 你当前的总奖励 x，则将 rewardValues[i] 加到 x 上（即 x = x + rewardValues[i]），并 标记 下标 i。
     * 以整数形式返回执行最优操作能够获得的 最大 总奖励。
     * 示例 1：
     * 输入：rewardValues = [1,1,3,3]
     * 输出：4
     * 解释：
     * 依次标记下标 0 和 2，总奖励为 4，这是可获得的最大值。
     * 示例 2：
     * 输入：rewardValues = [1,6,4,3,2]
     * 输出：11
     * 解释：
     * 依次标记下标 0、2 和 1。总奖励为 11，这是可获得的最大值。
     * 提示：
     * 1 <= rewardValues.length <= 2000
     * 1 <= rewardValues[i] <= 2000
     */
    public static int maxTotalReward(int[] rewardValues) {
        int n = rewardValues.length;
        if (n == 1) {
            return rewardValues[0];
        }

        Arrays.sort(rewardValues);
        int m = rewardValues[n - 1];
        // 最大数为m,还未选取m时,最大总奖励 < m，遍历结束最大总奖励 < 2m
        // dp[i] 只有1、0,值为1时表示奖励i可以获得
        int[] dp = new int[2 * m];
        dp[0] = 1;

        int ans = 0;
        for (int rewardValue : rewardValues) {
            // 遍历奖励小于当前数的项
            for (int i = 0; i < rewardValue; i++) {
                if (dp[i] == 1) {
                    dp[i + rewardValue] = 1;
                    ans = i + rewardValue;
                }
            }
        }
        return ans;
    }

    /**
     * LCR 110. 所有可能的路径
     * 中等 深度优先搜索 广度优先搜索 图 回溯
     * 给定一个有 n 个节点的有向无环图，用二维数组 graph 表示，请找到所有从 0 到 n-1 的路径并输出（不要求按顺序）。
     * graph 的第 i 个数组中的单元都表示有向图中 i 号节点所能到达的下一些结点（译者注：有向图是有方向的，即规定了 a→b 你就不能从 b→a ），
     * 若为空，就是没有下一个节点了。
     * 示例 1：
     * 输入：graph = [[1,2],[3],[3],[]] /// [[1, 2], [3, 0], [3, 0], [0, 0]]
     * 输出：[[0,1,3],[0,2,3]]
     * 解释：有两条路径 0 -> 1 -> 3 和 0 -> 2 -> 3
     * 示例 2：
     * 输入：graph = [[4,3,1],[3,2,4],[3],[4],[]]
     * 输出：[[0,4],[0,3,4],[0,1,3,4],[0,1,2,3,4],[0,1,4]]
     * 示例 3：
     * 输入：graph = [[1],[]]
     * 输出：[[0,1]]
     * 示例 4：
     * 输入：graph = [[1,2,3],[2],[3],[]]
     * 输出：[[0,1,2,3],[0,2,3],[0,3]]
     * 示例 5：
     * 输入：graph = [[1,3],[2],[3],[]]
     * 输出：[[0,1,2,3],[0,3]]
     * 提示：
     * n == graph.length
     * 2 <= n <= 15
     * 0 <= graph[i][j] < n
     * graph[i][j] != i
     * 保证输入为有向无环图 (GAD)
     */
    public static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> visited = new ArrayList<>();
        visited.add(0);
        backtrack(res, visited, graph, 0);
        return res;
    }

    private static void backtrack(List<List<Integer>> res, List<Integer> visited, int[][] graph, int i) {
        // 边界条件
        if (visited.get(visited.size() - 1) == graph.length - 1) {
            res.add(new ArrayList<>(visited));
            return;
        }

        // 递归
        for (int nextIndex : graph[i]) {
            // 条件剪枝
            if (nextIndex == 0) {
                continue;
            }
            // 设置值
            visited.add(nextIndex);
            // 递归
            backtrack(res, visited, graph, nextIndex);

            // 回溯
            visited.remove(visited.size() - 1);
        }
    }

    /**
     * 866. 回文质数
     * 中等
     * 给你一个整数 n ，返回大于或等于 n 的最小 回文质数。
     * 一个整数如果恰好有两个除数：1 和它本身，那么它是 质数 。注意，1 不是质数。
     * 例如，2、3、5、7、11 和 13 都是质数。
     * 一个整数如果从左向右读和从右向左读是相同的，那么它是 回文数 。
     * 例如，101 和 12321 都是回文数。
     * 测试用例保证答案总是存在，并且在 [2, 2 * 10^8] 范围内。
     * 示例 1：
     * 输入：n = 6
     * 输出：7
     * 示例 2：
     * 输入：n = 8
     * 输出：11
     * 示例 3：
     * 输入：n = 13
     * 输出：101
     * 提示：
     * 1 <= n <= 10^8
     */
    public int primePalindrome(int n) {
        while (true) {
            if (n == reverse(n) && isPrime(n)) {
                return n;
            }
            n++;
            if (10000000 < n && n < 100000000) {
                n = 100000000;
            }
        }
    }

    public boolean isPrime(int n) {
        if (n < 2) return false;
        int sqrt = (int) Math.sqrt(n);
        for (int d = 2; d <= sqrt; ++d) {
            if (n % d == 0) {
                return false;
            }
        }
        return true;
    }

    public int reverse(int n) {
        int ans = 0;
        while (n > 0) {
            ans = 10 * ans + (n % 10);
            n /= 10;
        }
        return ans;
    }

    /**
     * 1342. 将数字变成 0 的操作次数
     * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
     * 示例 1：
     * 输入：num = 14
     * 输出：6
     * 解释：
     * 步骤 1) 14 是偶数，除以 2 得到 7 。
     * 步骤 2） 7 是奇数，减 1 得到 6 。
     * 步骤 3） 6 是偶数，除以 2 得到 3 。
     * 步骤 4） 3 是奇数，减 1 得到 2 。
     * 步骤 5） 2 是偶数，除以 2 得到 1 。
     * 步骤 6） 1 是奇数，减 1 得到 0 。
     * 示例 2：
     * 输入：num = 8
     * 输出：4
     * 解释：
     * 步骤 1） 8 是偶数，除以 2 得到 4 。
     * 步骤 2） 4 是偶数，除以 2 得到 2 。
     * 步骤 3） 2 是偶数，除以 2 得到 1 。
     * 步骤 4） 1 是奇数，减 1 得到 0 。
     * 示例 3：
     * 输入：num = 123
     * 输出：12
     * 提示：
     * 0 <= num <= 10^6
     */
    public static int numberOfSteps(int num) {
        int ans = 0;
        while (num > 0) {
            num = num % 2 == 0 ? num >> 1 : num - 1;
            ans++;
        }
        return ans;
    }

    /**
     * 1047. 删除字符串中的所有相邻重复项
     * 简单
     * 给出由小写字母组成的字符串 s，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
     * 在 s 上反复执行重复项删除操作，直到无法继续删除。
     * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
     * 示例：
     * 输入："abbaca"
     * 输出："ca"
     * 解释：
     * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，
     * 其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
     * 提示：
     * 1 <= s.length <= 10^5
     * s 仅由小写英文字母组成。
     */
    public static String removeDuplicates(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (sb.length() == 0) {
                sb.append(s.charAt(i));
            } else if (c == sb.charAt(sb.length() - 1)) {
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 929. 独特的电子邮件地址
     * 简单
     * 每个 有效电子邮件地址 都由一个 本地名 和一个 域名 组成，以 '@' 符号分隔。除小写字母之外，电子邮件地址还可以含有一个或多个 '.' 或 '+' 。
     * 例如，在 alice@leetcode.com中， alice 是 本地名 ，而 leetcode.com 是 域名 。
     * 如果在电子邮件地址的 本地名 部分中的某些字符之间添加句点（'.'），则发往那里的邮件将会转发到本地名中没有点的同一地址。请注意，此规则 不适用于域名 。
     * 例如，"alice.z@leetcode.com” 和 “alicez@leetcode.com” 会转发到同一电子邮件地址。
     * 如果在 本地名 中添加加号（'+'），则会忽略第一个加号后面的所有内容。这允许过滤某些电子邮件。同样，此规则 不适用于域名 。
     * 例如 m.y+name@email.com 将转发到 my@email.com。
     * 可以同时使用这两个规则。
     * 给你一个字符串数组 emails，我们会向每个 emails[i] 发送一封电子邮件。返回实际收到邮件的不同地址数目。
     * 示例 1：
     * 输入：emails = ["test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"]
     * 输出：2
     * 解释：实际收到邮件的是 "testemail@leetcode.com" 和 "testemail@lee.tcode.com"。
     * 示例 2：
     * 输入：emails = ["a@leetcode.com","b@leetcode.com","c@leetcode.com"]
     * 输出：3
     * 提示：
     * 1 <= emails.length <= 100
     * 1 <= emails[i].length <= 100
     * emails[i] 由小写英文字母、'+'、'.' 和 '@' 组成
     * 每个 emails[i] 都包含有且仅有一个 '@' 字符
     * 所有本地名和域名都不为空
     * 本地名不会以 '+' 字符作为开头
     * 域名以 ".com" 后缀结尾。
     * 域名在 ".com" 后缀前至少包含一个字符
     */
    public int numUniqueEmails(String[] emails) {
        Set<String> set = new HashSet<>();
        for (String email : emails) {
            String[] split = email.split("@");
            int idx = split[0].indexOf("+");
            if (idx != -1) {
                split[0] = split[0].substring(0, idx);
            }
            String prefix = split[0].replaceAll("\\.", "");
            set.add(prefix + "@" + split[1]);
        }
        return set.size();
    }

    /**
     * 3341. 到达最后一个房间的最少时间 I
     * 中等
     * 有一个地窖，地窖中有 n x m 个房间，它们呈网格状排布。
     * 给你一个大小为 n x m 的二维数组 moveTime ，其中 moveTime[i][j] 表示在这个时刻 以后 你才可以 开始 往这个房间 移动 。
     * 你在时刻 t = 0 时从房间 (0, 0) 出发，每次可以移动到 相邻 的一个房间。在 相邻 房间之间移动需要的时间为 1 秒。
     * 请你返回到达房间 (n - 1, m - 1) 所需要的 最少 时间。
     * 如果两个房间有一条公共边（可以是水平的也可以是竖直的），那么我们称这两个房间是 相邻 的。
     * 示例 1：
     * 输入：moveTime = [[0,4],[4,4]]
     * 输出：6
     * 解释：
     * 需要花费的最少时间为 6 秒。
     * 在时刻 t == 4 ，从房间 (0, 0) 移动到房间 (1, 0) ，花费 1 秒。
     * 在时刻 t == 5 ，从房间 (1, 0) 移动到房间 (1, 1) ，花费 1 秒。
     * 示例 2：
     * 输入：moveTime = [[0,0,0],[0,0,0]]
     * 输出：3
     * 解释：
     * 需要花费的最少时间为 3 秒。
     * 在时刻 t == 0 ，从房间 (0, 0) 移动到房间 (1, 0) ，花费 1 秒。
     * 在时刻 t == 1 ，从房间 (1, 0) 移动到房间 (1, 1) ，花费 1 秒。
     * 在时刻 t == 2 ，从房间 (1, 1) 移动到房间 (1, 2) ，花费 1 秒。
     * 示例 3：
     * 输入：moveTime = [[0,1],[1,2]]
     * 输出：3
     * 提示：
     * 2 <= n == moveTime.length <= 50
     * 2 <= m == moveTime[i].length <= 50
     * 0 <= moveTime[i][j] <= 10^9
     */
    private static final int INF = 0x3f3f3f3f;

    // Dijkstra 算法变种
    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length, m = moveTime[0].length;
        int[][] d = new int[n][m];
        boolean[][] v = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], INF);
        }

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        d[0][0] = 0;
        PriorityQueue<State> q = new PriorityQueue<>();
        q.offer(new State(0, 0, 0));

        while (!q.isEmpty()) {
            State s = q.poll();
            if (v[s.x][s.y]) {
                continue;
            }
            v[s.x][s.y] = true;
            for (int[] dir : dirs) {
                int nx = s.x + dir[0];
                int ny = s.y + dir[1];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m) {
                    continue;
                }
                int dist = Math.max(d[s.x][s.y], moveTime[nx][ny]) + 1;
                if (d[nx][ny] > dist) {
                    d[nx][ny] = dist;
                    q.offer(new State(nx, ny, dist));
                }
            }
        }
        return d[n - 1][m - 1];
    }

    static class State implements Comparable<State> {
        int x, y, dis;

        State(int x, int y, int dis) {
            this.x = x;
            this.y = y;
            this.dis = dis;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.dis, other.dis);
        }
    }

    /**
     * 3046. 分割数组
     * 简单
     * 给你一个长度为 偶数 的整数数组 nums 。你需要将这个数组分割成 nums1 和 nums2 两部分，要求：
     * nums1.length == nums2.length == nums.length / 2 。
     * nums1 应包含 互不相同 的元素。
     * nums2也应包含 互不相同 的元素。
     * 如果能够分割数组就返回 true ，否则返回 false 。
     * 示例 1：
     * 输入：nums = [1,1,2,2,3,4]
     * 输出：true
     * 解释：分割 nums 的可行方案之一是 nums1 = [1,2,3] 和 nums2 = [1,2,4] 。
     * 示例 2：
     * 输入：nums = [1,1,1,1]
     * 输出：false
     * 解释：分割 nums 的唯一可行方案是 nums1 = [1,1] 和 nums2 = [1,1] 。但 nums1 和 nums2 都不是由互不相同的元素构成。因此，返回 false 。
     * 提示：
     * 1 <= nums.length <= 100
     * nums.length % 2 == 0
     * 1 <= nums[i] <= 100
     */
    public boolean isPossibleToSplit(int[] nums) {
        int[] counts = new int[101];
        for (int num : nums) {
            if (counts[num] == 2) {
                return false;
            }
            counts[num]++;
        }
        return true;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
