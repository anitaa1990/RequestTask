# RequestTask

This is a simple wrapper for HttpUrlConnection. Available in for Java and Android.

You can use the RequestTask class in any of your android projects. The wrapper is just a single class. 

<h2><b>Wrappers for</b></h2>
HTTP GET</br>
HTTP POST</br>
HTTP PUT</br>
HTTP DELETE</br>

<h3><b>Usage:</b></h3>
```RequestTask requestTask = new RequestTask();```

<b>HTTP GET:</b></br>

```
requestTask.get("sample_one.json", new RequestTask.TaskResponseListener() {
            @Override
            public void onSuccess(Object extra) {
                System.out.println("Successful " + extra);
            }

            @Override
            public void onError(RequestError error) {
                System.out.println("Error " + error.getHeaders());
                System.out.println("Error " + error.getResponseCode());
                System.out.println("Error " + error.getResponseMessage());
            }
        });
```
<b>HTTP POST:</b></br>

```
requestTask.post("sample_one.json", jsonObject, new RequestTask.TaskResponseListener() {
            @Override
            public void onSuccess(Object extra) {
                System.out.println("Successful " + extra);
            }

            @Override
            public void onError(RequestError error) {
                System.out.println("Error " + error.getHeaders());
                System.out.println("Error " + error.getResponseCode());
                System.out.println("Error " + error.getResponseMessage());
            }
        });
```
<b>HTTP PUT:</b></br>

```
requestTask.put("sample_one.json", new RequestTask.TaskResponseListener() {
            @Override
            public void onSuccess(Object extra) {
                System.out.println("Successful " + extra);
            }

            @Override
            public void onError(RequestError error) {
                System.out.println("Error " + error.getHeaders());
                System.out.println("Error " + error.getResponseCode());
                System.out.println("Error " + error.getResponseMessage());
            }
        });
```
<b>HTTP DELETE:</b></br>

```
requestTask.delete("sample_one.json", new RequestTask.TaskResponseListener() {
            @Override
            public void onSuccess(Object extra) {
                System.out.println("Successful " + extra);
            }

            @Override
            public void onError(RequestError error) {
                System.out.println("Error " + error.getHeaders());
                System.out.println("Error " + error.getResponseCode());
                System.out.println("Error " + error.getResponseMessage());
            }
        });
```

<p>You need to call the below method to start the async Request Task:</p>
```
requestTask.start();
```
</br>
Note: You need to pass the Base url to BASE_URL variable in RquestTask.java class<br/>
And that's it!

<b>Wrapper to store in Local Cache</b>
If you would like to save your request response to a local cache, you can use the ObjectUtil.java class:</br>
You cal access this class using:
```
ObjectUtil objDataStream = new ObjectUtil();
objDataStream.writeObjects(object,fileName);
```

You need to provide a fileName here. 
This filename needs to be the path where you would like to store this object.
The object could be anything.

In order to read the file from the local cache, you can use:
```
ObjectUtil objDataStream = new ObjectUtil();
return objDataStream.readObjects(fileName);
```

Or you could use the Util class provided in the library and use:
```
RequestUtil.writeObjectToDisk(fileName, result);
RequestUtil.readObjectFromDisk(result);
```

Enjoy!


