import java.util.*;

// 分析一个课程安排是否可以被完成，和 leetcode 207 完全一样。

// There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
// You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you
// must take course bi first if you want to take course ai.
//
// For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
// Return true if you can finish all courses. Otherwise, return false.

// 重点就是检查课程的依赖关系图中有没有圈，没有的话，就可以完成，有圈就没戏了（比如 1 需要 2， 2 需要 3， 3 需要 1）。
//

class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        HashMap<Integer, HashSet<Integer>> map = new HashMap<>(); // 用于存放依赖关系

        for (int i = 0; i < prerequisites.length; ++i) {
            int[] pair = prerequisites[i];
            int preReq = pair[1]; // 要想上target，需要先上preReq
            int target = pair[0];
            map.putIfAbsent(target, new HashSet<Integer>());
            map.get(target).add(preReq);
        }

        // 到此为止，map.get(course_1) 所得到的HashSet就是course_1所需的所有前置课程
        HashSet<Integer> visited = new HashSet<>(); // 记录已经分析过的节点，节省时间
        for (int course : map.keySet()) {
            if (!visited.contains(course)) {
                // use DFS to check if a circle exists in the dependency graph
                boolean hasCircle = this.DFS(course, map, visited, new HashSet<Integer>());
                if (hasCircle) return false;
            }
        }

        return true;
    }

    private boolean DFS (int node, HashMap<Integer, HashSet<Integer>> map, HashSet<Integer> visited, HashSet<Integer> path) {

        if (path.contains(node)) return true;
        if (visited.contains(node)) return false;

        path.add(node);

        HashSet<Integer> preReqs = map.get(node);
        boolean hasCircle = false;

        if (preReqs == null) {
            hasCircle = false;
        } else {
            for (int preReq : preReqs) {
                boolean res = DFS(preReq, map, visited, path);
                if (res) {
                    hasCircle = true;
                    break;
                }
            }
        }

        visited.add(node);
        path.remove(node);
        return hasCircle;
    }
}