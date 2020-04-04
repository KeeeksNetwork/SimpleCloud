package eu.thesimplecloud.base.wrapper.network.packets


import eu.thesimplecloud.api.CloudAPI
import eu.thesimplecloud.api.wrapper.IWritableWrapperInfo
import eu.thesimplecloud.base.wrapper.startup.Wrapper
import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.packet.packettype.ObjectPacket
import eu.thesimplecloud.clientserverapi.lib.promise.ICommunicationPromise

class PacketInSetWrapperName : ObjectPacket<String>() {

    override suspend fun handle(connection: IConnection): ICommunicationPromise<Unit> {
        val name = this.value ?: return contentException("value")
        Wrapper.instance.thisWrapperName = name
        Wrapper.instance.startProcessQueue()
        if (Wrapper.instance.isStartedInManagerDirectory()) {
            val thisWrapper = Wrapper.instance.getThisWrapper()
            thisWrapper as IWritableWrapperInfo
            thisWrapper.setTemplatesReceived(true)
            CloudAPI.instance.getWrapperManager().update(thisWrapper)
        }
        return unit()
    }
}