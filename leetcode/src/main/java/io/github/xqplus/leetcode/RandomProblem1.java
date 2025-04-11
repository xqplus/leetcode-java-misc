package io.github.xqplus.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
}
