package DevReg;

/*
Implement the missing code, denoted by ellipses. You may not modify the pre-existing code.
Hostnames consist of an alphabetic host type (e.g. "apibox") concatenated with a host number (eg: "apibox1", "apibox2", etc. are valid hostnames).

Your task is to create a class called Tracker that supports two operations:

allocate(hostType), which reserves the first available hostname and returns it;
deallocate(hostname), which should release that hostname back into the pool.
The input for this task is an array of sequential queries in string form, where a query of type "A <hostType>" means a call to tracker.allocate(<hostType>), and query of type "D <hostname>" means a call tracker.deallocate(<hostname>). The output should be an array of return values of all allocate calls.

There is already a prewritten piece of code that handles the input / output and makes allocate / deallocate calls, so you just need to create the Tracker class that implements them.

Note that deallocating a non-allocated hostname is a valid operation.

Example

For queries = ["A apibox", "A apibox", "D apibox1", "A apibox", "A sitebox"], the output should be
solution(queries) = ["apibox1", "apibox2", "apibox1", "sitebox1"].

This is how it should work for an already initialized Tracker class instance tracker:

>> tracker.allocate('apibox');
"apibox1"

>> tracker.allocate('apibox');
"apibox2"

>> tracker.deallocate('apibox1');

>> tracker.allocate('apibox');
"apibox1"

>> tracker.allocate('sitebox');
"sitebox1"
Input/Output

[execution time limit] 3 seconds (java)

[memory limit] 1 GB

[input] array.string queries

An array of strings representing queries to the tracker.

queries[i] = "A <hostType>" means that you should call tracker.allocate(<hostType>) and return the reserved hostname.
queries[i] = "D <hostname>" means that you should call tracker.deallocate(<hostname>) and return nothing.
It is guaranteed that all host numbers of the deallocating queries won't exceed 999.

Guaranteed constraints:
1 ≤ queries.length ≤ 103.

[output] array.string

An array of responses from the tracker.
 */

import java.util.*;

public class host {

  List<String> solution(String[] queries) {
    Tracker tracker = new Tracker();
    List<String> ans = new ArrayList<>();
    for (String query : queries) {
      String[] task = query.split(" ");
      if ("A".equals(task[0])) {
        ans.add(tracker.allocate(task[1]));
      }
      if ("D".equals(task[0])) {
        tracker.deallocate(task[1]);
      }
    }
    return ans;
  }

  public static void main(String[] args) {
    host h = new host();
    String[] queries = {"A apibox", "A apibox", "D apibox1", "A apibox", "A sitebox"};
    System.out.println(h.solution(queries)); // ["apibox1", "apibox2", "apibox1", "sitebox1"]
  }
}

class Tracker {
  private ArrayList<String> hostName = new ArrayList<>();

  public String allocate(String taskName) {
    int idx = 1;
    String tempTask = taskName + idx;
    while (hostName.contains(tempTask)) {
      idx++;
      tempTask = taskName + idx;
    }
    hostName.add(tempTask);
    return tempTask;
  }

  public void deallocate(String taskName) {
    hostName.remove(taskName);
  }
}
