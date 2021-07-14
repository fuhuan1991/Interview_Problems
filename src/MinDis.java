import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MinDis {

    public static void main(String[] args) {
        // Test
        Point[] points = new Point[7];

        points[0] = new Point(1, 1);
        points[1] = new Point(1, 9);
        points[2] = new Point(2, 5);
        points[3] = new Point(3, 1);
        points[4] = new Point(4, 4);
        points[5] = new Point(5, 8);
        points[6] = new Point(6, 2);

        // 预处理，基于x轴坐标排序，便于分治法实施
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return (p1.x > p2.x) ? 1 : (p1.x == p2.x) ? 0 : -1;
            }

        });
        // 测试
        System.out.println(divide(0, points.length-1, points));
    }

    /**
     * 求平面上距离最近的两个点
     *
     */
    public static double divide(int left, int right, Point[] points) {
        // 当前最小两点距离，初始值设置为无穷大
        double curMinDis = 1e20;
        // 如果只有一个点，则不存在最近两点距离，返回无穷大
        if (left == right) {
            return curMinDis;
        }
        // 这里是判断是否为只有两个点，如果只有两个点的话那么直接求解。
        if (left + 1 == right) {
            return distance(points[left], points[right]);
        }

        // 首先在左右区间上分别调用divide，得到左右集合的最小距离
        int middle = (left + right) >> 1; // >> 1 = 除以2
        double leftMinDis = divide(left, middle, points);
        double rightMinDis = divide(middle, right, points);

        curMinDis = (leftMinDis <= rightMinDis) ? leftMinDis : rightMinDis;

        // curMinDis不一定是最终结果。从左右集合中各取一个点，它们之间的距离可能比curMinDis更小
        // 为了进一步缩小搜索的范围，我们首先排除一些点。Points是一个Point对象的数组，并且是按照X轴排序的。
        // 如果一个点和points[middle]的距离大于curMinDis，那么最小距离注定和这个点没关系了
        List<Integer> validPointIndex = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[middle].x - points[i].x) <= curMinDis) {
                validPointIndex.add(i);
            }
        }

        // 遍历 validPointIndex 中所有可能的组合
        for (int i = 0; i < validPointIndex.size() - 1; i++) {
            for (int j = i + 1; j < validPointIndex.size(); j++) {
                // 如果区间内的两点y轴距离大于curMinDis，则没必要计算了，因为，它们的距离肯定大于curMinDis，
                if (Math.abs(points[validPointIndex.get(i)].y - points[validPointIndex.get(j)].y) > curMinDis) {
                    continue;
                }
                double tempDis = distance(points[validPointIndex.get(i)], points[validPointIndex.get(j)]);

                curMinDis = (tempDis < curMinDis) ? tempDis : curMinDis;
            }
        }

        return curMinDis;
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y) + (p2.x - p1.x) * (p2.x - p1.x));
    }
}

class Point {
    public int x;
    public int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}