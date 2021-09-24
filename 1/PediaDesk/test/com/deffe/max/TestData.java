package com.deffe.max;

public class TestData
{
    public static String getRequestContext()
    {
       return "{\n" +
               "\t\"version\": \"1.0\",\n" +
               "\t\"session\": {\n" +
               "\t\t\"new\": true,\n" +
               "\t\t\"sessionId\": \"amzn1.echo-api.session.25dc2dc3-2b64-4375-9186-1f71887bd843\",\n" +
               "\t\t\"application\": {\n" +
               "\t\t\t\"applicationId\": \"amzn1.ask.skill.6ea76dfb-7c21-4da2-9708-22478a6822b0\"\n" +
               "\t\t},\n" +
               "\t\t\"user\": {\n" +
               "\t\t\t\"userId\": \"amzn1.ask.account.AF6AX6ZJZL5JJHXWWTCGUVV7YWLSRRB5ZASC63ZD3SH5AYVBAIH6HXFHLSLLKQKJEM6P6CNOMD33V75POZJNNL6Q4QVF7NLKNTXN65X4C5GKNHU7CCSVBWDMQ4C6RW27CDN3EQAHGLBRJQK7FDPU5GY72JMMJOIP5E6JTHFE3E3GCO4HSPNX2FQFTYRBMQS6GQP45IAPERW37JY\"\n" +
               "\t\t}\n" +
               "\t},\n" +
               "\t\"context\": {\n" +
               "\t\t\"AudioPlayer\": {\n" +
               "\t\t\t\"playerActivity\": \"IDLE\"\n" +
               "\t\t},\n" +
               "\t\t\"System\": {\n" +
               "\t\t\t\"application\": {\n" +
               "\t\t\t\t\"applicationId\": \"amzn1.ask.skill.6ea76dfb-7c21-4da2-9708-22478a6822b0\"\n" +
               "\t\t\t},\n" +
               "\t\t\t\"user\": {\n" +
               "\t\t\t\t\"userId\": \"amzn1.ask.account.AF6AX6ZJZL5JJHXWWTCGUVV7YWLSRRB5ZASC63ZD3SH5AYVBAIH6HXFHLSLLKQKJEM6P6CNOMD33V75POZJNNL6Q4QVF7NLKNTXN65X4C5GKNHU7CCSVBWDMQ4C6RW27CDN3EQAHGLBRJQK7FDPU5GY72JMMJOIP5E6JTHFE3E3GCO4HSPNX2FQFTYRBMQS6GQP45IAPERW37JY\"\n" +
               "\t\t\t},\n" +
               "\t\t\t\"device\": {\n" +
               "\t\t\t\t\"deviceId\": \"amzn1.ask.device.AFCRMJUK5YNPPEAIZ5ETWUSUZZHKGEWRIGQFBFVPJ7Y3WAS64RJCGZPEHRHWQJ65BHUH3EDLM4EF5WPSI7ZRW5OJC2BAC77K3VUPMRJCBH2ISGIB4GG5JSWTWXJAGCTELIYNBTJVKKVRF346KXPAYMXM3LVUQFKM2TSCUY6IXVZGBYOMUUXYK\",\n" +
               "\t\t\t\t\"supportedInterfaces\": {\n" +
               "\t\t\t\t\t\"AudioPlayer\": {}\n" +
               "\t\t\t\t}\n" +
               "\t\t\t},\n" +
               "\t\t\t\"apiEndpoint\": \"https://api.amazonalexa.com\",\n" +
               "\t\t\t\"apiAccessToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjZlYTc2ZGZiLTdjMjEtNGRhMi05NzA4LTIyNDc4YTY4MjJiMCIsImV4cCI6MTUzNTk4MDUyOSwiaWF0IjoxNTM1OTc2OTI5LCJuYmYiOjE1MzU5NzY5MjksInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUZDUk1KVUs1WU5QUEVBSVo1RVRXVVNVWlpIS0dFV1JJR1FGQkZWUEo3WTNXQVM2NFJKQ0daUEVIUkhXUUo2NUJIVUgzRURMTTRFRjVXUFNJN1pSVzVPSkMyQkFDNzdLM1ZVUE1SSkNCSDJJU0dJQjRHRzVKU1dUV1hKQUdDVEVMSVlOQlRKVktLVlJGMzQ2S1hQQVlNWE0zTFZVUUZLTTJUU0NVWTZJWFZaR0JZT01VVVhZSyIsInVzZXJJZCI6ImFtem4xLmFzay5hY2NvdW50LkFGNkFYNlpKWkw1SkpIWFdXVENHVVZWN1lXTFNSUkI1WkFTQzYzWkQzU0g1QVlWQkFJSDZIWEZITFNMTEtRS0pFTTZQNkNOT01EMzNWNzVQT1pKTk5MNlE0UVZGN05MS05UWE42NVg0QzVHS05IVTdDQ1NWQldETVE0QzZSVzI3Q0ROM0VRQUhHTEJSSlFLN0ZEUFU1R1k3MkpNTUpPSVA1RTZKVEhGRTNFM0dDTzRIU1BOWDJGUUZUWVJCTVFTNkdRUDQ1SUFQRVJXMzdKWSJ9fQ.HzhE5v7h89w41oo3AbiwvLfA1vVEwllPuIefpep1JbMN336gUi8cgvU4QAPztfFdphhDahR752CQfvJ1SFYTSRPHwNjpOPHTbvw8AThC-FkyvRkspFgDtr3N60nLhGunAo4HTfgRsqy9zPs8eRMWgUZO2U35WsgFOjpunZ5eMNxZFaGG8nWIS1kXv5lM82_M5lkmiTulCctwL6DAPU2HKdgIqP7yNXzBxnpR3oNQ4q311FEVXMHz9kLHZsBLbCnetwjxpthojjixH8o5v7ZF11V_442UBWzjIs5nSemSfLFI2ele9No1tQnS2F-7ESsU1tDezoUiIpLK6vhpRJTw_w\"\n" +
               "\t\t}\n" +
               "\t},\n" +
               "\t\"request\": {\n" +
               "\t\t\"type\": \"IntentRequest\",\n" +
               "\t\t\"requestId\": \"amzn1.echo-api.request.33fb2ca4-cfd9-412e-b6e9-f64e5a98bbcb\",\n" +
               "\t\t\"timestamp\": \"2018-09-03T12:15:29Z\",\n" +
               "\t\t\"locale\": \"en-US\",\n" +
               "\t\t\"intent\": {\n" +
               "\t\t\t\"name\": \"MovieNameIntent\",\n" +
               "\t\t\t\"confirmationStatus\": \"NONE\",\n" +
               "\t\t\t\"slots\": {\n" +
               "\t\t\t\t\"movie_name\": {\n" +
               "\t\t\t\t\t\"name\": \"movie_name\",\n" +
               "\t\t\t\t\t\"value\": \"say avengers crew\",\n" +
               "\t\t\t\t\t\"confirmationStatus\": \"NONE\"\n" +
               "\t\t\t\t}\n" +
               "\t\t\t}\n" +
               "\t\t}\n" +
               "\t}\n" +
               "}";
    }
}
