package io.github.xqplus.leetcode;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Simple3 {

    /**
     * 2037. 使每位学生都有座位的最少移动次数
     * 一个房间里有 n 个 空闲 座位和 n 名 站着的 学生，房间用一个数轴表示。给你一个长度为 n 的数组 seats ，其中 seats[i] 是第 i 个座位的位置。
     * 同时给你一个长度为 n 的数组 students ，其中 students[j] 是第 j 位学生的位置。
     * 你可以执行以下操作任意次：
     * 增加或者减少第 i 位学生的位置，每次变化量为 1 （也就是将第 i 位学生从位置 x 移动到 x + 1 或者 x - 1）
     * 请你返回使所有学生都有座位坐的 最少移动次数 ，并确保没有两位学生的座位相同。
     * 请注意，初始时有可能有多个座位或者多位学生在 同一 位置。
     * 示例 1：
     * 输入：seats = [3,1,5], students = [2,7,4]
     * 输出：4
     * 解释：学生移动方式如下：
     * - 第一位学生从位置 2 移动到位置 1 ，移动 1 次。
     * - 第二位学生从位置 7 移动到位置 5 ，移动 2 次。
     * - 第三位学生从位置 4 移动到位置 3 ，移动 1 次。
     * 总共 1 + 2 + 1 = 4 次移动。
     * 示例 2：
     * 输入：seats = [4,1,5,9], students = [1,3,2,6]
     * 输出：7
     * 解释：学生移动方式如下：
     * - 第一位学生不移动。
     * - 第二位学生从位置 3 移动到位置 4 ，移动 1 次。
     * - 第三位学生从位置 2 移动到位置 5 ，移动 3 次。
     * - 第四位学生从位置 6 移动到位置 9 ，移动 3 次。
     * 总共 0 + 1 + 3 + 3 = 7 次移动。
     * 示例 3：
     * 输入：seats = [2,2,6,6], students = [1,3,2,6]
     * 输出：4
     * 解释：学生移动方式如下：
     * - 第一位学生从位置 1 移动到位置 2 ，移动 1 次。
     * - 第二位学生从位置 3 移动到位置 6 ，移动 3 次。
     * - 第三位学生不移动。
     * - 第四位学生不移动。
     * 总共 1 + 3 + 0 + 0 = 4 次移动。
     * 提示：
     * n == seats.length == students.length
     * 1 <= n <= 100
     * 1 <= seats[i], students[j] <= 100
     */
    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);
        int ans = 0;
        for (int i = 0; i < seats.length; i++) {
            ans += Math.abs(seats[i] - students[i]);
        }
        return ans;
    }

    /**
     * 2710. 移除字符串中的尾随零
     * 给你一个用字符串表示的正整数 num ，请你以字符串形式返回不含尾随零的整数 num 。
     * 示例 1：
     * 输入：num = "51230100"
     * 输出："512301"
     * 解释：整数 "51230100" 有 2 个尾随零，移除并返回整数 "512301" 。
     * 示例 2：
     * 输入：num = "123"
     * 输出："123"
     * 解释：整数 "123" 不含尾随零，返回整数 "123" 。
     * 提示：
     * 1 <= num.length <= 1000
     * num 仅由数字 0 到 9 组成
     * num 不含前导零
     */
    public String removeTrailingZeros(String num) {
        int n = num.length(), i = n - 1;
        while (num.charAt(i) == '0') {
            i--;
        }
        return i == n - 1 ? num : num.substring(0, i + 1);
    }

    /**
     * 1539. 第 k 个缺失的正整数
     * 给你一个 严格升序排列 的正整数数组 arr 和一个整数 k 。
     * 请你找到这个数组里第 k 个缺失的正整数。
     * 示例 1：
     * 输入：arr = [2,3,4,7,11], k = 5
     * 输出：9
     * 解释：缺失的正整数包括 [1,5,6,8,9,10,12,13,...] 。第 5 个缺失的正整数为 9 。
     * 示例 2：
     * 输入：arr = [1,2,3,4], k = 2
     * 输出：6
     * 解释：缺失的正整数包括 [5,6,7,...] 。第 2 个缺失的正整数为 6 。
     * 提示：
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 1000
     * 1 <= k <= 1000
     * 对于所有 1 <= i < j <= arr.length 的 i 和 j 满足 arr[i] < arr[j]
     * 进阶：
     * 你可以设计一个时间复杂度小于 O(n) 的算法解决此问题吗？
     */
    public static int findKthPositive(int[] arr, int k) {
        int n = arr.length;
        if (arr[n - 1] - n < k) {
            return k + n;
        }
        // 二分变法
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + ((r - l) >> 1);
            int m = arr[mid] - mid - 1; // 表示arr[mid]之前有多少个缺失数
            if (m < k) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r + k;
    }

    /**
     * 1005. K 次取反后最大化的数组和
     * 给你一个整数数组 nums 和一个整数 k ，按以下方法修改该数组：
     * 选择某个下标 i 并将 nums[i] 替换为 -nums[i] 。
     * 重复这个过程恰好 k 次。可以多次选择同一个下标 i 。
     * 以这种方式修改数组后，返回数组 可能的最大和 。
     * 示例 1：
     * 输入：nums = [4,2,3], k = 1
     * 输出：5
     * 解释：选择下标 1 ，nums 变为 [4,-2,3] 。
     * 示例 2：
     * 输入：nums = [3,-1,0,2], k = 3
     * 输出：6
     * 解释：选择下标 (1, 2, 2) ，nums 变为 [3,1,0,2] 。
     * 示例 3：
     * 输入：nums = [2,-3,-1,5,-4], k = 2
     * 输出：13
     * 解释：选择下标 (1, 4) ，nums 变为 [2,3,-1,5,4] 。
     * 提示：
     * 1 <= nums.length <= 10^4
     * -100 <= nums[i] <= 100
     * 1 <= k <= 10^4
     */
    public int largestSumAfterKNegations(int[] nums, int k) {
        // 设nums中负数的个数为n，先取反min(n,k)个最大的负数，记m=k-n,若m>0, m是奇数取当前最小的非负数取反，偶数不变
        int ans = 0, min = 100;
        int[] counts = new int[101];
        for (int num : nums) {
            if (num < 0) { // 负数计数
                counts[-num]++;
            }
            ans += num; // 总和计数
            min = Math.min(min, Math.abs(num)); // 负数全正后最小的数
        }
        for (int i = 100; i >= 1 && k > 0; i--) {
            if (counts[i] == 0) {
                continue;
            }
            int m = Math.min(counts[i], k);
            ans += i * m * 2;
            k -= m;
        }
        if (k > 0 && (k & 1) == 1) { // k>n的情况
            ans -= min * 2;
        }
        return ans;
    }

    /**
     * 674. 最长连续递增序列
     * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
     * 连续递增的子序列 可以由两个下标 l 和 r（l < r）确定，如果对于每个 l <= i < r，都有 nums[i] < nums[i + 1] ，
     * 那么子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]] 就是连续递增子序列。
     * 示例 1：
     * 输入：nums = [1,3,5,4,7]
     * 输出：3
     * 解释：最长连续递增序列是 [1,3,5], 长度为3。
     * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为 5 和 7 在原数组里被 4 隔开。
     * 示例 2：
     * 输入：nums = [2,2,2,2,2]
     * 输出：1
     * 解释：最长连续递增序列是 [2], 长度为1。
     * 提示：
     * 1 <= nums.length <= 10^4
     * -10^9 <= nums[i] <= 10^9
     */
    public int findLengthOfLCIS(int[] nums) {
        int ans = 1, cnt = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                cnt++;
                continue;
            }
            ans = Math.max(ans, cnt);
            cnt = 1;
        }
        return Math.max(ans, cnt);
    }

    /**
     * 2600. K 件物品的最大和
     * 袋子中装有一些物品，每个物品上都标记着数字 1 、0 或 -1 。
     * 给你四个非负整数 numOnes 、numZeros 、numNegOnes 和 k 。
     * 袋子最初包含：
     * numOnes 件标记为 1 的物品。
     * numZeros 件标记为 0 的物品。
     * numNegOnes 件标记为 -1 的物品。
     * 现计划从这些物品中恰好选出 k 件物品。返回所有可行方案中，物品上所标记数字之和的最大值。
     * 示例 1：
     * 输入：numOnes = 3, numZeros = 2, numNegOnes = 0, k = 2
     * 输出：2
     * 解释：袋子中的物品分别标记为 {1, 1, 1, 0, 0} 。取 2 件标记为 1 的物品，得到的数字之和为 2 。
     * 可以证明 2 是所有可行方案中的最大值。
     * 示例 2：
     * 输入：numOnes = 3, numZeros = 2, numNegOnes = 0, k = 4
     * 输出：3
     * 解释：袋子中的物品分别标记为 {1, 1, 1, 0, 0} 。取 3 件标记为 1 的物品，1 件标记为 0 的物品，得到的数字之和为 3 。
     * 可以证明 3 是所有可行方案中的最大值。
     * 提示：
     * 0 <= numOnes, numZeros, numNegOnes <= 50
     * 0 <= k <= numOnes + numZeros + numNegOnes
     */
    public int kItemsWithMaximumSum(int numOnes, int numZeros, int numNegOnes, int k) {
        return k <= numOnes + numZeros ? Math.min(numOnes, k) : numOnes - Math.min(numNegOnes, k - numOnes - numZeros);
    }

    /**
     * 2264. 字符串中最大的 3 位相同数字
     * 给你一个字符串 num ，表示一个大整数。如果一个整数满足下述所有条件，则认为该整数是一个 优质整数 ：
     * 该整数是 num 的一个长度为 3 的 子字符串 。
     * 该整数由唯一一个数字重复 3 次组成。
     * 以字符串形式返回 最大的优质整数 。如果不存在满足要求的整数，则返回一个空字符串 "" 。
     * 注意：
     * 子字符串 是字符串中的一个连续字符序列。
     * num 或优质整数中可能存在 前导零 。
     * 示例 1：
     * 输入：num = "6777133339"
     * 输出："777"
     * 解释：num 中存在两个优质整数："777" 和 "333" 。
     * "777" 是最大的那个，所以返回 "777" 。
     * 示例 2：
     * 输入：num = "2300019"
     * 输出："000"
     * 解释："000" 是唯一一个优质整数。
     * 示例 3：
     * 输入：num = "42352338"
     * 输出：""
     * 解释：不存在长度为 3 且仅由一个唯一数字组成的整数。因此，不存在优质整数。
     * 提示：
     * 3 <= num.length <= 1000
     * num 仅由数字（0 - 9）组成
     */
    public String largestGoodInteger(String num) {
        char[] cs = num.toCharArray();
        int n = cs.length - 3, k = -1;
        for (int i = 0; i <= n; i++) {
            if (cs[i] == cs[i + 1] && cs[i] == cs[i + 2]) {
                k = Math.max(k, cs[i]);
            }
        }
        if (k == -1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append((char) k);
        }
        return sb.toString();
    }

    /**
     * 3168. 候诊室中的最少椅子数
     * 给你一个字符串 s，模拟每秒钟的事件 i：
     * 如果 s[i] == 'E'，表示有一位顾客进入候诊室并占用一把椅子。
     * 如果 s[i] == 'L'，表示有一位顾客离开候诊室，从而释放一把椅子。
     * 返回保证每位进入候诊室的顾客都能有椅子坐的 最少 椅子数，假设候诊室最初是 空的 。
     * 示例 1：
     * 输入：s = "EEEEEEE"
     * 输出：7
     * 解释：
     * 每秒后都有一个顾客进入候诊室，没有人离开。因此，至少需要 7 把椅子。
     * 示例 2：
     * 输入：s = "ELELEEL"
     * 输出：2
     * 解释：
     * 假设候诊室里有 2 把椅子。下表显示了每秒钟等候室的状态。
     * 秒	事件	候诊室的人数	可用的椅子数
     * 0	Enter	1	1
     * 1	Leave	0	2
     * 2	Enter	1	1
     * 3	Leave	0	2
     * 4	Enter	1	1
     * 5	Enter	2	0
     * 6	Leave	1	1
     * 示例 3：
     * 输入：s = "ELEELEELLL"
     * 输出：3
     * 解释：
     * 假设候诊室里有 3 把椅子。下表显示了每秒钟等候室的状态。
     * 秒	事件	候诊室的人数	可用的椅子数
     * 0	Enter	1	2
     * 1	Leave	0	3
     * 2	Enter	1	2
     * 3	Enter	2	1
     * 4	Leave	1	2
     * 5	Enter	2	1
     * 6	Enter	3	0
     * 7	Leave	2	1
     * 8	Leave	1	2
     * 9	Leave	0	3
     * 提示：
     * 1 <= s.length <= 50
     * s 仅由字母 'E' 和 'L' 组成。
     * s 表示一个有效的进出序列。
     */
    public int minimumChairs(String s) {
        int ans = 0, c = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'E') {
                c++;
                if (c > ans) ans = c;
            } else {
                c--;
            }
        }
        return ans;
    }

    /**
     * 2798. 满足目标工作时长的员工数目
     * 公司里共有 n 名员工，按从 0 到 n - 1 编号。每个员工 i 已经在公司工作了 hours[i] 小时。
     * 公司要求每位员工工作 至少 target 小时。
     * 给你一个下标从 0 开始、长度为 n 的非负整数数组 hours 和一个非负整数 target 。
     * 请你用整数表示并返回工作至少 target 小时的员工数。
     * 示例 1：
     * 输入：hours = [0,1,2,3,4], target = 2
     * 输出：3
     * 解释：公司要求每位员工工作至少 2 小时。
     * - 员工 0 工作 0 小时，不满足要求。
     * - 员工 1 工作 1 小时，不满足要求。
     * - 员工 2 工作 2 小时，满足要求。
     * - 员工 3 工作 3 小时，满足要求。
     * - 员工 4 工作 4 小时，满足要求。
     * 共有 3 位满足要求的员工。
     * 示例 2：
     * 输入：hours = [5,1,4,2,2], target = 6
     * 输出：0
     * 解释：公司要求每位员工工作至少 6 小时。
     * 共有 0 位满足要求的员工。
     * 提示：
     * 1 <= n == hours.length <= 50
     * 0 <= hours[i], target <= 10^5
     */
    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int ans = 0;
        for (int hour : hours) {
            if (hour >= target) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 1544. 整理字符串
     * 给你一个由大小写英文字母组成的字符串 s 。
     * 一个整理好的字符串中，两个相邻字符 s[i] 和 s[i+1]，其中 0<= i <= s.length-2 ，要满足如下条件:
     * 若 s[i] 是小写字符，则 s[i+1] 不可以是相同的大写字符。
     * 若 s[i] 是大写字符，则 s[i+1] 不可以是相同的小写字符。
     * 请你将字符串整理好，每次你都可以从字符串中选出满足上述条件的 两个相邻 字符并删除，直到字符串整理好为止。
     * 请返回整理好的 字符串 。题目保证在给出的约束条件下，测试样例对应的答案是唯一的。
     * 注意：空字符串也属于整理好的字符串，尽管其中没有任何字符。
     * 示例 1：
     * 输入：s = "leEeetcode"
     * 输出："leetcode"
     * 解释：无论你第一次选的是 i = 1 还是 i = 2，都会使 "leEeetcode" 缩减为 "leetcode" 。
     * 示例 2：
     * 输入：s = "abBAcC"
     * 输出：""
     * 解释：存在多种不同情况，但所有的情况都会导致相同的结果。例如：
     * "abBAcC" --> "aAcC" --> "cC" --> ""
     * "abBAcC" --> "abBA" --> "aA" --> ""
     * 示例 3：
     * 输入：s = "s"
     * 输出："s"
     * 提示：
     * 1 <= s.length <= 100
     * s 只包含小写和大写英文字母
     */
    public String makeGood(String s) {
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            if (!stack.isEmpty() && Math.abs(s.charAt(i) - stack.peek()) == 32) {
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }
        return sb.toString();
    }

    /**
     * 1360. 日期之间隔几天
     * 请你编写一个程序来计算两个日期之间隔了多少天。
     * 日期以字符串形式给出，格式为 YYYY-MM-DD，如示例所示。
     * 示例 1：
     * 输入：date1 = "2019-06-29", date2 = "2019-06-30"
     * 输出：1
     * 示例 2：
     * 输入：date1 = "2020-01-15", date2 = "2019-12-31"
     * 输出：15
     * 提示：
     * 给定的日期是 1971 年到 2100 年之间的有效日期。
     */
    public int daysBetweenDates(String date1, String date2) {
        long between = ChronoUnit.DAYS.between(LocalDate.parse(date1), LocalDate.parse(date2));
        return (int) Math.abs(between);
    }

    /**
     * 804. 唯一摩尔斯密码词
     * 国际摩尔斯密码定义一种标准编码方式，将每个字母对应于一个由一系列点和短线组成的字符串， 比如:
     * 'a' 对应 ".-" ，
     * 'b' 对应 "-..." ，
     * 'c' 对应 "-.-." ，以此类推。
     * 为了方便，所有 26 个英文字母的摩尔斯密码表如下：
     * [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
     * 给你一个字符串数组 words ，每个单词可以写成每个字母对应摩尔斯密码的组合。
     * 例如，"cab" 可以写成 "-.-..--..." ，(即 "-.-." + ".-" + "-..." 字符串的结合)。我们将这样一个连接过程称作 单词翻译 。
     * 对 words 中所有单词进行单词翻译，返回不同 单词翻译 的数量。
     * 示例 1：
     * 输入: words = ["gin", "zen", "gig", "msg"]
     * 输出: 2
     * 解释:
     * 各单词翻译如下:
     * "gin" -> "--...-."
     * "zen" -> "--...-."
     * "gig" -> "--...--."
     * "msg" -> "--...--."
     * 共有 2 种不同翻译, "--...-." 和 "--...--.".
     * 示例 2：
     * 输入：words = ["a"]
     * 输出：1
     * 提示：
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 12
     * words[i] 由小写英文字母组成
     */
    public int uniqueMorseRepresentations(String[] words) {
        String[] passTable = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
                "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        Set<String> set = new HashSet<>();
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                sb.append(passTable[word.charAt(i) - 'a']);
            }
            set.add(sb.toString());
        }
        return set.size();
    }

    /**
     * 1089. 复写零
     * 给你一个长度固定的整数数组 arr ，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
     * 注意：请不要在超过该数组长度的位置写入元素。请对输入的数组 就地 进行上述修改，不要从函数返回任何东西。
     * 示例 1：
     * 输入：arr = [1,0,2,3,0,4,5,0] 10023045 10023004
     * 输出：[1,0,0,2,3,0,0,4]
     * 解释：调用函数后，输入的数组将被修改为：[1,0,0,2,3,0,0,4]
     * 示例 2：
     * 输入：arr = [1,2,3]
     * 输出：[1,2,3]
     * 解释：调用函数后，输入的数组将被修改为：[1,2,3]
     * 提示：
     * 1 <= arr.length <= 10^4
     * 0 <= arr[i] <= 9
     */
    public void duplicateZeros(int[] arr) {
//        int n = arr.length, validZeros = 0;
//        for (int i = 0; i < n; i++) {
//            if (arr[i] == 0) {
//                if (i + validZeros + 1 >= n) {
//                    break;
//                }
//                validZeros++;
//            }
//        }
//        int l = n - 1 - validZeros, r = n - 1;
//        while (l < r) {
//            if (arr[l] == 0) {
//                arr[r--] = 0;
//            }
//            arr[r--] = arr[l--];
//        }

        int n = arr.length, zeros = 0;
        for (int num : arr) {
            if (num == 0) {
                zeros++;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (zeros == 0) {
                break;
            }
            if (arr[i] == 0) {
                if (i + zeros < n) {
                    arr[i + zeros] = 0;
                }
                zeros--;
            }
            if (i + zeros < n) {
                arr[i + zeros] = arr[i];
            }
        }
    }

    /**
     * 206. 反转链表
     * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
     * 示例 1：
     * 输入：head = [1,2,3,4,5]
     * 输出：[5,4,3,2,1]
     * 示例 2：
     * 输入：head = [1,2]
     * 输出：[2,1]
     * 示例 3：
     * 输入：head = []
     * 输出：[]
     * 提示：
     * 链表中节点的数目范围是 [0, 5000]
     * -5000 <= Node.val <= 5000
     * 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, next;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    /**
     * LCR 034. 验证外星语词典
     * 某种外星语也使用英文小写字母，但可能顺序 order 不同。字母表的顺序（order）是一些小写字母的排列。
     * 给定一组用外星语书写的单词 words，以及其字母表的顺序 order，只有当给定的单词在这种外星语中按字典序排列时，返回 true；否则，返回 false。
     * 示例 1：
     * 输入：words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
     * 输出：true
     * 解释：在该语言的字母表中，'h' 位于 'l' 之前，所以单词序列是按字典序排列的。
     * 示例 2：
     * 输入：words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
     * 输出：false
     * 解释：在该语言的字母表中，'d' 位于 'l' 之后，那么 words[0] > words[1]，因此单词序列不是按字典序排列的。
     * 示例 3：
     * 输入：words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
     * 输出：false
     * 解释：当前三个字符 "app" 匹配时，第二个字符串相对短一些，然后根据词典编纂规则 "apple" > "app"，因为 'l' > '∅'，其中 '∅' 是空白字符，
     * 定义为比任何其他字符都小（更多信息）。
     * 提示：
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 20
     * order.length == 26
     * 在 words[i] 和 order 中的所有字符都是英文小写字母。
     */
    public boolean isAlienSorted(String[] words, String order) {
        for (int i = 1; i < words.length; i++) {
            if (!alienEquals(words[i - 1], words[i], order)) {
                return false;
            }
        }
        return true;
    }

    private boolean alienEquals(String a, String b, String order) {
        int len = Math.min(a.length(), b.length());
        for (int i = 0; i < len; i++) {
            int idxA = order.indexOf(a.charAt(i)), idxB = order.indexOf(b.charAt(i));
            if (idxA != idxB) {
                return idxA < idxB;
            }
        }
        return a.length() <= b.length();
    }

    /**
     * 917. 仅仅反转字母
     * 给你一个字符串 s ，根据下述规则反转字符串：
     * 所有非英文字母保留在原有位置。
     * 所有英文字母（小写或大写）位置反转。
     * 返回反转后的 s 。
     * 示例 1：
     * 输入：s = "ab-cd"
     * 输出："dc-ba"
     * 示例 2：
     * 输入：s = "a-bC-dEf-ghIj"
     * 输出："j-Ih-gfE-dCba"
     * 示例 3：
     * 输入：s = "Test1ng-Leet=code-Q!"
     * 输出："Qedo1ct-eeLg=ntse-T!"
     * 提示
     * 1 <= s.length <= 100
     * s 仅由 ASCII 值在范围 [33, 122] 的字符组成
     * s 不含 '\"' 或 '\\'
     */
    public String reverseOnlyLetters(String s) {
        char[] cs = s.toCharArray();
        int left = 0, right = cs.length - 1;
        while (left < right) {
            while (left < right && !Character.isLetter(cs[left])) {
                left++;
            }
            while (right > left && !Character.isLetter(cs[right])) {
                right--;
            }
            if (left == right) {
                break;
            }
            char tmp = cs[left];
            cs[left] = cs[right];
            cs[right] = tmp;
            left++;
            right--;
        }
        return new String(cs);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {1, 7, 11, 14, 29, 31, 40, 44};
        System.out.println(findKthPositive(nums, 20));
    }
}
