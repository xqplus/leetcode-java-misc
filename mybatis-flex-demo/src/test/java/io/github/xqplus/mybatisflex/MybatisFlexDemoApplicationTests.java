package io.github.xqplus.mybatisflex;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import io.github.xqplus.mybatisflex.entity.Account;
import io.github.xqplus.mybatisflex.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.xqplus.mybatisflex.entity.table.AccountTableDef.ACCOUNT;

@SpringBootTest
class MybatisFlexDemoApplicationTests {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    void contextLoads() {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .where(QueryCondition.create(new QueryColumn()))
                .where(ACCOUNT.AGE.eq(18));
        Account account = accountMapper.selectOneByQuery(queryWrapper);
        System.out.println(account);
    }

}
