import { Eureka } from 'eureka-js-client';

const eurekaClient = new Eureka({
    instance: {
        app: process.env.SERVICE_NAME,
        hostName: process.env.EUREKA_HOST,
        ipAddr: '127.0.0.1',
        port: {
            '$': Number(process.env.PORT),
            '@enabled': true
        },
        vipAddress: 'ms-payment',
        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },

        statusPageUrl: `http://localhost:${process.env.PORT}/health`,
    },
    eureka: {
        host: process.env.EUREKA_HOST,
        port: process.env.EUREKA_PORT,
        servicePath: '/eureka/apps/',
        maxRetries: 10,
        requestRetryDelay: 2000,
    },
    registerWithEureka: true,
    fetchRegistry: false,
});

export default eurekaClient;