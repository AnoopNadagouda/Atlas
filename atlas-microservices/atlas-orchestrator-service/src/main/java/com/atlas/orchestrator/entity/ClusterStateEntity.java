package com.atlas.orchestrator.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "aios_cluster_state")
public class ClusterStateEntity {

    @Id
    private String clusterId;

    @Column(name = "active_nodes")
    private int activeNodes;

    @Column(name = "active_services")
    private int activeServices;

    @Column(name = "overall_cpu_usage")
    private double overallCpuUsage;

    @Column(name = "overall_memory_usage")
    private double overallMemoryUsage;

    @Column(name = "status")
    private String status;

    @Column(name = "last_check_time", nullable = false)
    private Instant lastCheckTime;

    public ClusterStateEntity() {
    }

    public String getClusterId() { return clusterId; }
    public void setClusterId(String clusterId) { this.clusterId = clusterId; }

    public int getActiveNodes() { return activeNodes; }
    public void setActiveNodes(int activeNodes) { this.activeNodes = activeNodes; }

    public int getActiveServices() { return activeServices; }
    public void setActiveServices(int activeServices) { this.activeServices = activeServices; }

    public double getOverallCpuUsage() { return overallCpuUsage; }
    public void setOverallCpuUsage(double overallCpuUsage) { this.overallCpuUsage = overallCpuUsage; }

    public double getOverallMemoryUsage() { return overallMemoryUsage; }
    public void setOverallMemoryUsage(double overallMemoryUsage) { this.overallMemoryUsage = overallMemoryUsage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getLastCheckTime() { return lastCheckTime; }
    public void setLastCheckTime(Instant lastCheckTime) { this.lastCheckTime = lastCheckTime; }
}
