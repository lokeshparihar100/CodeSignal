package DevReg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
 A service is responsible for running tasks, and we're interested in the order those tasks will run. A task is defined by its:

s: earliest start time
d: total duration of execution
p: priority of execution
The service is greedy in running tasks, such that it will start a runnable task if idle. At most one task can be run at any time, and all tasks will be run exactly once. The service will not start a task before its earliest start time s, however the task may be run any time thereafter. Once started, no other tasks may be scheduled for the duration d while the task runs to completion.

If two or more tasks are available to be run, then the next task is selected in order of the following criteria:

highest priority p
earliest start time s
lowest duration of execution d
lowest index in the input
The input will consist of three arrays s, d, and p, all consisting of n elements (1 ≤ n ≤ 1000), where index i specifies the value for task i. The output should be an array of length n consisting of the order in which the tasks will be run, where a task is identified by its index i in the input.

For example, the input:

s: [0, 2, 2]
d: [1, 1, 1]
p: [0, 0, 1]
Defines three tasks. Task 0 is run first at time 0. Both task 1 and 2 can be run at time 2, however since task 2 has a higher priority, it's run next, followed by task 1. Therefore, the expected output is:

[0, 2, 1]
[execution time limit] 3 seconds (java)

[memory limit] 1 GB

[input] array.integer s

An array of length n, where task i's earliest start time is 0 ≤ s[i] ≤ 9999999.

[input] array.integer d

An array of length n, where task i's duration of execution is 1 ≤ d[i] ≤ 10000000.

[input] array.integer p

An array of length n, where task i's priority of execution is 0 ≤ p[i] ≤ 3.

[output] array.integer

An array of length n consisting of the order in which the tasks were run, where a task is identified by its index in the input.
 */
public class taskrun {
    public static int[] solution(int[] s, int[] d, int[] p) {
        int len = s.length;
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            tasks.add(new Task(s[i], d[i], p[i], i));
        }

        tasks.sort(Comparator.comparingInt((Task t) -> t.startTime).thenComparingInt(t -> t.index));

        PriorityQueue<Task> pqueue = new PriorityQueue<>((a, b) -> {
            if (b.priority != a.priority)
                return b.priority - a.priority;

            if (a.startTime != b.startTime)
                return a.startTime - b.startTime;

            if (a.duration != b.duration)
                return a.duration - b.duration;

            return a.index - b.index;
        });

        List<Integer> res = new ArrayList<>();
        int currTime = 0, i = 0;

        while (res.size() < len) {
            while (i < len && tasks.get(i).startTime <= currTime) {
                pqueue.offer(tasks.get(i));
                i++;
            }
            if (!pqueue.isEmpty()) {
                Task task = pqueue.poll();
                res.add(task.index);
                currTime += task.duration;
            } else {
                currTime = tasks.get(i).startTime;
            }
        }
        return res.stream().mapToInt(n -> n).toArray();
    }

    public static void main(String[] args) {
        int[] s = {0, 2, 2};
        int[] d = {1, 1, 1};
        int[] p = {0, 0, 1};
        int[] res = solution(s, d, p);
        for (int i : res) {
            System.out.print(i + " "); // 0 2 1
        }
    }
}

class Task {
    int startTime, duration, priority, index;

    Task(int startTime, int duration, int priority, int index) {
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
        this.index = index;
    }
}
