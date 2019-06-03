
# Special Reservations
## Latest Release: v1.0.0

**_Special Reservations_** allows you to reserve “Special” slots for your donators, VIP, and other prestigious members.

## -How does it work?-
Use /reserve [integer] to allocate your maximum capacity to hold a [integer] number of slots for users with permissions<br />
_Example:_ Let’s say we have a maximum capacity of 10 on the server, by typing in /reserve 3, we now will have 7 regular slots and 3 reserved slots.<br />

The reserved slots are for users with the special_reserve.specialbypass permission. <br />

Anyone with the special bypass permission now can inhabit either a regular slot or a reserved slot giving them 10 possible slots to be placed into, whereas regular members will have 7 slots they can go into.<br />

This plugin always fills reserved slot with reserved players first, before placing them within a regular slot. <br />

There is also a special permission for staff: special_reservation.staffbypass, that allows a staff to bypass being full, but not take away any space for a regular or special user.<br />

## -Features-

### -Custom Full messages-

Do _/specialfullmessage [message]_ to change what a special user sees when there is no more space available to them, which is displayed when there are no regular or special slots for them to go into.<br />

Do _/regularfullmessage [message]_ to change what a regular user sees when there is no more space available to them, which is displayed when there are no regular slots for them to go into.<br />

### -Color-

All above custom messaging works with ‘&’ color formatting. Ex: “/specialfullmessage &3its full!” will display “its full” in aqua blue. You can google “Minecraft color codes” for more choices.<br />

### -Slot Checking-

Want to know who online is special, staff, or even regular? Try /specialonline, /staffonline, /regularonline to display the number of users with that permission and a list of their names.<br />

### -Togglable-

/rstoggle <true/false> allows you to turn on or off the reserved slots plugin. False will unallocated the number of slots, whereas True will reallocate the slots again.<br />

### -Reconfigurable Number of Slots-

/reserve [Integer] allocates a integer number of slots out of your total maximum capacity. This is a hard cap, meaning you can never have more slots then you do maximum capacity. Instead, if you so choose, you could have the same number of reserved slots as you do maximum capacity, meaning no player can join without being given the special bypass or staff bypass permission.<br />

---
## -How to install-
1. Click the Release tab on this page in GitHub
2. Find the Latest release version, and under Assets at the bottom, click on the SleepCast-x.x.x.jar
3. Place downloaded .jar file into your server's plugins folder
4. Restart your server.
### Enjoy!

