package com.atlas.domain.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantQuota {
    private long maxStorageBytes;
    private long maxDocuments;
    private long maxMonthlyQueries;
    private long maxMonthlyCrawlPages;
}
