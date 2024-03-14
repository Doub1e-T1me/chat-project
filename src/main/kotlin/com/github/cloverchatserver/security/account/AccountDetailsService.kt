package com.github.cloverchatserver.security.account

import com.github.cloverchatserver.domain.account.business.AccountMapper
import com.github.cloverchatserver.domain.account.business.AccountService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountDetailsService(
    private val accountService: AccountService,
    private val accountMapper: AccountMapper,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): AccountDetails {
        val account = accountService.findByUsername(username).let {
            accountMapper.toDto(it!!)
        }

        val roles = ArrayList<GrantedAuthority>().apply {
            add(SimpleGrantedAuthority(account.role.name))
        }

        return AccountDetails(account, roles)
    }
}
