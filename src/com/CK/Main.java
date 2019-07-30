package com.CK;

public class Main {

    public static void main(String[] args) {
        //        String s = "227";
//        String s = "012";
//        String s = "10";
//        String s = "110";
//        String s = "1212";
//        String s = "4757562545844617494555774581341211511296816786586787755257741178599337186486723247528324612117156948";
//        String s = "301";
//        String s = "1*";
//        String s = "*";
//        String s = "***";
        String s = "**********1111111111";
        Solution solution = new Solution();
        System.out.println(solution.numDecodings(s));
    }
}

class Solution {
    final int M = 1000000007;

    public int numDecodings(String s) {
        if (s.length() == 0) return 0;
        long[] dp = new long[s.length()];
        for (int i = 0; i < s.length(); i++) {
            // dp[0]
            if (i == 0 && s.charAt(i) == '0') return 0;
            else if (i == 0 && s.charAt(i) == '*') dp[i] = 9;
            else if (i == 0) dp[i] = 1;

                //dp[1]
            else if (i == 1) {
                String subS = s.substring(0, i + 1);
                if (s.charAt(i) == '*' && s.charAt(i - 1) == '*') {
                    dp[i] = 9 * 9 + 15;
                } else if (s.charAt(i) == '*' && s.charAt(i - 1) != '*') {
                    if (s.charAt(i - 1) == '0') dp[i] = 0;
                    else if (s.charAt(i - 1) == '1') dp[i] = 18;
                    else if (s.charAt(i - 1) == '2') dp[i] = 15;
                    else dp[i] = 9;
                } else if (s.charAt(i) != '*' && s.charAt(i - 1) == '*') {
                    if (s.charAt(i) == '0') dp[i] = 2;
                    else if (s.charAt(i) >= '7' && s.charAt(i) <= '9') dp[i] = 1 + 9;
                    else dp[i] = 9 + 2;
                } else {
                    if (Integer.parseInt(subS) <= 9) dp[i] = 0;
                    else if (s.charAt(i) == '0' && s.charAt(i - 1) != '1' && s.charAt(i - 1) != '2') dp[i] = 0;
                    else if ((Integer.parseInt(subS) > 10 && Integer.parseInt(subS) < 20) || (Integer.parseInt(subS) > 20 && Integer.parseInt(subS) < 27))
                        dp[i] = 2;
                    else dp[i] = 1;
                }
            }

            //dp[i] i>1
            else {
                String subS = s.substring(i - 1, i + 1);
                if (s.charAt(i) == '*' && s.charAt(i - 1) == '*') {
                    dp[i] = 9 * dp[i - 1];
                    dp[i] = (dp[i] + (dp[i - 2] * 15)) % M;
                } else if (s.charAt(i) == '*' && s.charAt(i - 1) != '*') {
                    if (s.charAt(i - 1) == '1') dp[i] = (9 * dp[i - 1] + 9 * dp[i - 2]) % M;
                    else if (s.charAt(i - 1) == '2') dp[i] = (9 * dp[i - 1] + 6 * dp[i - 2]) % M;
                    else dp[i] = (9 * dp[i - 1]) % M;
                } else if (s.charAt(i) != '*' && s.charAt(i - 1) == '*') {
                    if (s.charAt(i) == '0') dp[i] = (2 * dp[i - 2]) % M;
                    else if (s.charAt(i) >= '7' && s.charAt(i) <= '9') dp[i] = (dp[i - 2] + dp[i - 1]) % M;
                    else dp[i] = (dp[i - 1] + 2 * dp[i - 2]) % M;
                } else {
                    if (s.charAt(i) == '0') {
                        if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2') dp[i] = (dp[i - 2]) % M;
                        else dp[i] = 0;
                    } else if ((Integer.parseInt(subS) > 10 && Integer.parseInt(subS) < 20) || (Integer.parseInt(subS) > 20 && Integer.parseInt(subS) < 27))
                        dp[i] = (dp[i - 1] + dp[i - 2]) % M;
                    else dp[i] = (dp[i - 1]) % M;
                }
            }
        }
        return (int) dp[s.length() - 1];
    }
}


class Solution2 {
    int M = 1000000007;

    public int numDecodings(String s) {
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : s.charAt(0) == '0' ? 0 : 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                dp[i + 1] = 9 * dp[i];
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + 9 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2')
                    dp[i + 1] = (dp[i + 1] + 6 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + 15 * dp[i - 1]) % M;
            } else {
                dp[i + 1] = s.charAt(i) != '0' ? dp[i] : 0;
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + (s.charAt(i) <= '6' ? 2 : 1) * dp[i - 1]) % M;
            }
        }
        return (int) dp[s.length()];
    }
}

class Solution3 {
    public int numDecodings(String s) {
        /* initial conditions */
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        if (s.charAt(0) == '0') {
            return 0;
        }
        dp[1] = (s.charAt(0) == '*') ? 9 : 1;

        /* bottom up method */
        for (int i = 2; i <= s.length(); i++) {
            char first = s.charAt(i - 2);
            char second = s.charAt(i - 1);

            // For dp[i-1]
            if (second == '*') {
                dp[i] += 9 * dp[i - 1];
            } else if (second > '0') {
                dp[i] += dp[i - 1];
            }

            // For dp[i-2]
            if (first == '*') {
                if (second == '*') {
                    dp[i] += 15 * dp[i - 2];
                } else if (second <= '6') {
                    dp[i] += 2 * dp[i - 2];
                } else {
                    dp[i] += dp[i - 2];
                }
            } else if (first == '1' || first == '2') {
                if (second == '*') {
                    if (first == '1') {
                        dp[i] += 9 * dp[i - 2];
                    } else { // first == '2'
                        dp[i] += 6 * dp[i - 2];
                    }
                } else if (((first - '0') * 10 + (second - '0')) <= 26) {
                    dp[i] += dp[i - 2];
                }
            }

            dp[i] %= 1000000007;
        }
        /* Return */
        return (int) dp[s.length()];
    }
}