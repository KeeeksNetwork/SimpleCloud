/*
 * MIT License
 *
 * Copyright (C) 2020 The SimpleCloud authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package eu.thesimplecloud.api.service.impl

import eu.thesimplecloud.api.property.IProperty
import eu.thesimplecloud.api.property.Property
import eu.thesimplecloud.api.service.ICloudService
import eu.thesimplecloud.api.service.ServiceState
import eu.thesimplecloud.api.service.version.ServiceVersion
import eu.thesimplecloud.api.utils.time.Timestamp
import eu.thesimplecloud.jsonlib.JsonLib
import eu.thesimplecloud.jsonlib.JsonLibExclude
import java.util.*
import kotlin.collections.HashMap

data class DefaultCloudService(
        private val groupName: String,
        private val serviceNumber: Int,
        private val uniqueId: UUID,
        private val templateName: String,
        private var wrapperName: String?,
        private var port: Int,
        private val maxMemory: Int,
        private var maxPlayers: Int,
        private var motd: String,
        private val serviceVersion: ServiceVersion
) : ICloudService {

    private var serviceState = ServiceState.PREPARED
    private var onlineCount = 0
    @Volatile
    private var usedMemory = 0
    private var authenticated = false
    @JsonLibExclude
    private var lastPlayerUpdate = Timestamp()

    var propertyMap = HashMap<String, Property<*>>()

    override fun getGroupName(): String = this.groupName

    override fun getServiceNumber(): Int = this.serviceNumber

    override fun getUniqueId(): UUID = this.uniqueId

    override fun getServiceVersion(): ServiceVersion = this.serviceVersion

    override fun getTemplateName(): String = this.templateName

    override fun getPort(): Int = this.port

    fun setPort(port: Int) {
        this.port = port
    }

    override fun getWrapperName(): String? = this.wrapperName

    fun setWrapperName(wrapperName: String?) {
        this.wrapperName = wrapperName
    }

    override fun getState(): ServiceState = this.serviceState

    override fun setState(serviceState: ServiceState) {
        this.serviceState = serviceState
    }

    override fun getOnlineCount(): Int {
        return this.onlineCount
    }

    override fun setOnlineCount(amount: Int) {
        this.onlineCount = amount
    }

    override fun getMaxPlayers(): Int {
        return this.maxPlayers
    }

    override fun setMaxPlayers(amount: Int) {
        this.maxPlayers = amount
    }

    override fun getMOTD(): String = this.motd

    override fun setMOTD(motd: String) {
        this.motd = motd
    }

    override fun isAuthenticated(): Boolean = this.authenticated

    override fun setAuthenticated(authenticated: Boolean) {
        this.authenticated = authenticated
    }

    override fun getMaxMemory(): Int = this.maxMemory

    override fun getUsedMemory(): Int = this.usedMemory

    override fun getLastPlayerUpdate(): Timestamp = this.lastPlayerUpdate

    override fun setLastPlayerUpdate(timeStamp: Timestamp) {
        this.lastPlayerUpdate = timeStamp
    }


    override fun toString(): String {
        return JsonLib.fromObject(this).getAsJsonString()
    }

    override fun getProperties(): Map<String, IProperty<*>> = this.propertyMap

    override fun <T : Any> setProperty(name: String, value: T): IProperty<T> {
        require(value !is Property<*>) { "Cannot set ${value::class.java.name} as property" }
        val property = Property(value)
        this.propertyMap[name] = property
        return property
    }

    override fun clearProperties() {
        this.propertyMap.clear()
    }

    override fun removeProperty(name: String) {
        this.propertyMap.remove(name)
    }

    fun setUsedMemory(usedMemory: Int) {
        this.usedMemory = usedMemory
    }

}