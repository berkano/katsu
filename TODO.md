# To Do - features

- [x] review changes to framework during LD33 to check stuff that needs cleaning
- [x] review LD33 impl code for useful stuff to move to framework
- [x] support for finding nearest entity of class C - extract from Combat.teleportToNearestBed
- [x] support overriding KUI - move out LD33 specific logic to LD33UI class inc. messagereplacements stuff

# To Do - tech debt

- [ ] separation of concerns out of K. A lot of it could be moved to KGraphics
- [ ] review actual need for interfaces? or just extend from base impls
- [ ] KMovementConstrainer probably doesn't need to be its own class
- [ ] probably don't need nearly so much static stuff all over the place
- [ ] KSound is empty - probably can strip out