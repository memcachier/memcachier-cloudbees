MemCachier CloudBees Example
-------------

This code shows how to use [MemCachier](http://memcachier.com/) on [CloudBees](http://www.cloudbees.com/).

To deploy on CloudBees, first create a [CloudBees account](http://www.cloudbees.com/) and
sample application named `memcachiercloudbees` in the `RUN@cloud` service.

Then clone this repo, modify `src/main/webapp/WEB-INF/cloudbees-web.xml`, and run the following commands:

    $ cd memcachier-cloudbees
    $ bees # you will be prompted to login
    $ mvn bees:deploy -Dbees.appid=memcachiercloudbees
