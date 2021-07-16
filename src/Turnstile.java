import java.util.*;

/**
 * 某大楼一层有个转门儿，有人想出去，有人想进来。
 * 每一个时间点都有可能会出现一个人在门口等待离开或是进来，同一时刻只能有一个人使用转门。
 * 转门有一个方向，可以是向内（进入）或向外（离开）。转门有惯性会保持方向。
 * 如果 t 时刻只有一个人从转门离去或进来，并且当时对面没人和ta抢，这个人就可以顺利通过（不管此时转门状态如何）。再此之后，转门方向会被设定在相应的方向。
 * 如果 t 时刻有两个人同时在转门前等候，一个想出去，一个想进来，并且此时转门的方向是向外的，那么这一时刻只有想离开的那个人可以通过，想进来的人必须等待，反之亦然。
 * 转门初始的状态是向外的（离开的方向）
 *
 * 不要问我为什么转门同时只能进去一个人。。。:(
 *
 * 输入参数有3个：
 *  numCustomers： 总人数，包括想出去的和想进来的
 *  arrTime： 一个数组，记录顾客出现的时间。arrTime[i]即为编号为 i 的顾客出现在门口的时间点
 *  direction： 一个数组，其中只包含 0 或 1，记录顾客的行走方向，1 为出去， 0 为进入。 direction[i] 即为编号为 i 的顾客的行走方向
 *  numCustomers = arrTime.length = direction.length
 *
 * 最终的结果是一个数组，result。result[i] 为编号为i的顾客通过转门的时间
 */

public class Turnstile {

    public static int[] run(int numCustomers, int[] arrTime, int[] direction) {

        // 我们可以使用两个Deque来模拟进入和离开的队列
        int[] result = new int[numCustomers];
        Queue<Customer> Qin = new ArrayDeque<>();
        Queue<Customer> Qout = new ArrayDeque<>();
        int remainingCustomers = numCustomers;
        int turnstileDirection = 0; // default direction is out

        for (int i = 0; i < arrTime.length; i++) {
            Customer c = new Customer(i, arrTime[i], direction[i]);
            if (c.direction == 0) {
                Qin.add(c);
            } else {
                Qout.add(c);
            }
        }

        int t = 0; // used to determine timeStamp
        while (remainingCustomers > 0) {

            // get the next possible customers
            Customer customerIn = Qin.peek();
            Customer customerOut = Qout.peek();

            // if a customer's arriving time is larger than t, it means this person has not arrived at this time point yet.
            if (customerIn != null && customerIn.arrTime > t) customerIn = null;
            if (customerOut != null && customerOut.arrTime > t) customerOut = null;

            // at this time point, 2 persons in different directions are at the turnstile.
            // only one can pass the turnstile.
            // it determined by the current state of the turnstile
            if (customerIn != null && customerOut != null) {

                if (turnstileDirection == 1) {
                    // used for exit
                    // the person in exit queue shall leave
                    Customer c = Qout.poll();
                    result[c.index] = t;
                } else {
                    // used to entry
                    // the person in entry queue shall enter
                    Customer c = Qin.poll();
                    result[c.index] = t;
                }

                t++; // time elapses
                remainingCustomers--;
                continue;
            } else if (customerIn != null) {
                // one person wants to enter, no one wants to leave
                Customer c = Qin.poll();
                result[c.index] = t;
                remainingCustomers--;
                turnstileDirection = 0; // reset turnstile direction to 0
                t++;
                continue;
            } else if (customerOut != null) {
                // one person wants to leave, no one wants to enter
                Customer c = Qout.poll();
                result[c.index] = t;
                remainingCustomers--;
                turnstileDirection = 1; // reset turnstile direction to 1
                t++;
                continue;
            } else {
                // no one at the turnstile right now, nothing happens
                // this section can be improved. we can try to figure out the next time point that someone comes and
                // increase t directly to that point instead of increasing t by 1.
                t++;
                continue;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(run(4, new int[]{0,0,1,5}, new int[]{0,1,1,0})));
        System.out.println(Arrays.toString(run(5, new int[]{0,1,1,3,3}, new int[]{0,1,0,0,1})));
    }
}

class Customer {
    int index;
    int arrTime;
    int direction;

    Customer(int index, int arrTime, int direction) {
        this.index = index;
        this.arrTime = arrTime;
        this.direction = direction;
    }
}

