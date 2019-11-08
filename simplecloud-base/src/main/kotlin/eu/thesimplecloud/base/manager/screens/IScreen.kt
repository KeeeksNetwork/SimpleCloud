package eu.thesimplecloud.base.manager.screens

import eu.thesimplecloud.lib.screen.ICommandExecutable
import eu.thesimplecloud.lib.utils.Nameable

interface IScreen : Nameable {

    override fun getName(): String = this.getCommandExecutable().getName()

    /**
     * Returns the [ICommandExecutable] this screen is recording.
     */
    fun getCommandExecutable(): ICommandExecutable

    /**
     * Executes a command on this screen.
     */
    fun executeCommand(command: String) = getCommandExecutable().executeCommand(command)

    /**
     * Adds a message to this screen
     */
    fun addMessage(message: String)

    /**
     * Returns a list of all saved messages.
     */
    fun getAllSavedMessages(): List<String>

}