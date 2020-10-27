package com.legends.cloud.web.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class PrometheusMetricsBiz {


    static final Counter requestTotalCountCollector=Counter.build()
                .name("herion_http_requests_total")
                .labelNames("path", "method", "code")
                .help("http请求总计数").register();


    static final Counter requestCounter = Counter.build()
            .name("module_core_http_requests_total").labelNames("path", "method", "code")
            .help("Total requests.").register();

    static final Gauge inprogressRequests = Gauge.build()
            .name("module_core_http_inprogress_requests").labelNames("path", "method", "code")
            .help("Inprogress requests.").register();

    static final Gauge requestTime = Gauge.build()
            .name("module_core_http_requests_costTime").labelNames("path", "method", "code")
            .help("requests cost time.").register();

    static final Histogram requestLatencyHistogram = Histogram.build().labelNames("path", "method", "code")
            .name("module_core_http_requests_latency_seconds_histogram").help("Request latency in seconds.")
            .register();

    static final Summary requestLatency = Summary.build()
            .name("module_core_http_requests_latency_seconds_summary")
            .quantile(0.5, 0.05)
            .quantile(0.9, 0.01)
            .labelNames("path", "method", "code")
            .help("Request latency in seconds.").register();

    private Histogram.Timer histogramRequestTimer;

    private Summary.Timer summaryTimer;

    private Gauge.Timer gaugeTimer;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response){
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();

        requestTotalCountCollector.labels(requestURI, method,status+"").inc();

        inprogressRequests.labels(requestURI, method, String.valueOf(status)).inc();
        histogramRequestTimer = requestLatencyHistogram.labels(requestURI, method, String.valueOf(status)).startTimer();
        summaryTimer = requestLatency.labels(requestURI, method, String.valueOf(status)).startTimer();
        gaugeTimer = requestTime.labels(requestURI, method, String.valueOf(status)).startTimer();
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response){

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();

        requestCounter.labels(requestURI, method, String.valueOf(status)).inc();
        inprogressRequests.labels(requestURI, method, String.valueOf(status)).dec();
        histogramRequestTimer.observeDuration();
        summaryTimer.observeDuration();
        gaugeTimer.setDuration();
    }
}