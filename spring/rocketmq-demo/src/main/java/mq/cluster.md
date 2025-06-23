
before:
```

4台机器,2m-2s
集群中的m-s使用的是sync方式(async)
rocket的message.setWaitStoreMsgOK(false);  约等于 kafka的ACK=0

常识理论：
linux环境，防火墙关闭，主机名，hosts
强调：主机名不要包含下划线_    -可以的

node01~04
4个配置文件：
自动去ns上注册
storepath的规划
logs文件路径的规划

/var  存储 数据

mkdir -p  /var/rocketmq/{logs,store/{commitlog,consumequeue,index}}
mkdir -p  /var/rocketmq/{logs,store/{commitlog,index,consumequeue}}

config:

brokerClusterName=DefaultCluster
#这个要主从一致
brokerName=broker-a
#master是0，slave是>0的
brokerId=0
deleteWhen=04
fileReservedTime=48
#SYNC_MASTER，ASYNC_MASTER，SLAVE
brokerRole=SYNC_MASTER
#降低机器的磁盘IO瓶颈
flushDiskType=ASYNC_FLUSH
namesrvAddr=192.168.150.11:9876;192.168.150.12:9876;192.168.150.13:9876
storePathRootDir=/var/rocketmq/store
storePathCommitLog=/var/rocketmq/store/commitlog
storePathIndex=/var/rocketmq/store/index
storePathConsumeQueue=/var/rocketmq/store/consumequeue

注意：
对4个配置文件进行相关配置修改，注意细节
----
 sed -i 's#${user.home}#/var/rocketmq#g'  *.xml

----
vi runbroker.sh  :
JAVA_OPT="${JAVA_OPT} -server -Xms1g -Xmx1g"
---

cd /opt
 scp -r ./rocketmq-4.9.2/  node02:/opt
  scp -r ./rocketmq-4.9.2/  node03:/opt
   scp -r ./rocketmq-4.9.2/  node04:/opt
 
------------
启动:
nameserver
node01~node03
broker
node01~02  broker-a  0,1  master slave
node01:
    mqbroker  -c  /opt/rocketmq-4.9.2/conf/22conf/broker-a.properties
node02:
    mqbroker  -c /opt/rocketmq-4.9.2/conf/22conf/broker-a-s.properties
node03~04  broker-b  0,1  master slave
node03:
    mqbroker -c /opt/rocketmq-4.9.2/conf/22conf/broker-b.properties
node04:
    mqbroker  -c /opt/rocketmq-4.9.2/conf/22conf/broker-b-s.properties

---
brokerRole=SYNC_MASTER  |  ASYNC_MASTER
message.setWaitStoreMsgOK(true);
SYNC_MASTER: producer send 到master后，master同步给slave，然后给producer返回OK ack=-1
ASYNC_MASTER：producer send 到master后，master异步给slave同步，并给producer返回OK  ack=1
message.setWaitStoreMsgOK(false);  ack=0
集群也是该sync，或者async的同步形式，只不过ok不需要等了

```