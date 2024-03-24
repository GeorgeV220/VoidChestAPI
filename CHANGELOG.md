# [2.4.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.3.0...v2.4.0) (2024-03-24)


### Features

* **economy:** refactor economy interfaces and classes ([210b51e](https://github.com/GeorgeV220/VoidChestAPI/commit/210b51e2e3740f926c86d9ffff1f97ff8c0d931b))

# [2.3.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0...v2.3.0) (2024-03-22)


### Features

* **IVoidChestBank:** add getSimpleName method ([c528dc3](https://github.com/GeorgeV220/VoidChestAPI/commit/c528dc397c14b9989c304d057c9392aba36cb3ce))

# [2.2.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.1.0...v2.2.0) (2024-03-22)


### Bug Fixes

* **api:** add null check for blockInventory() ([2cb50b1](https://github.com/GeorgeV220/VoidChestAPI/commit/2cb50b16821d00a5e3e2e3777172a237c910341a))
* **BoundingBox:** Change clone method to public ([4b82ed6](https://github.com/GeorgeV220/VoidChestAPI/commit/4b82ed612147f603dca146287b8cfac7d017bb3d))
* **boundingbox:** use absolute values for coordinates ([0512b46](https://github.com/GeorgeV220/VoidChestAPI/commit/0512b46b01afbd36a2cdd9e097eca98c61b57b95))
* **boundingbox:** use absolute values for coordinates ([a717765](https://github.com/GeorgeV220/VoidChestAPI/commit/a71776554830dc125f9843f2df65ebd9e108ee59))
* **filters:** replace SerializableItemStack with VoidInventoryItemStack ([1fa904b](https://github.com/GeorgeV220/VoidChestAPI/commit/1fa904b46b71dec978e84f1397fe1c8e885cb4a2))
* **SerializableLocation:** change toLocation() to return a clone of the location to prevent changes to the original object ([e86a9a2](https://github.com/GeorgeV220/VoidChestAPI/commit/e86a9a2def87a1ebf0e8f7dbc5eaf5da32a47386))
* **storage/IVoidStorage:** Update JavaDocs and mark white/blacklist inventories forRemoval ([49c0474](https://github.com/GeorgeV220/VoidChestAPI/commit/49c047487ac088ffd77d8e755611a3d31b527f91))
* **Upgrade.java:** change return type of upgrade method ([83ddfff](https://github.com/GeorgeV220/VoidChestAPI/commit/83ddfffd8802dd52331b4c53f594ba97a6f45c2f))
* **VoidChestAPI:** Update the library version and add Entity to Minecraft Scheduler ([4e0382d](https://github.com/GeorgeV220/VoidChestAPI/commit/4e0382d3e97261456241e236fb41985bbcb4f607))


### Features

* add and remove methods for paginated void inventory ([42053eb](https://github.com/GeorgeV220/VoidChestAPI/commit/42053ebf596fbf2a1dbc376a8e80baecde65c09f))
* add BoundingBox class for 3D space representation ([c60231d](https://github.com/GeorgeV220/VoidChestAPI/commit/c60231d0f16c192d2a8b0ff01c61c6ed25c361ad))
* add filter manager to VoidChestAPI ([39d4554](https://github.com/GeorgeV220/VoidChestAPI/commit/39d45540f74467e485348a76796942708b23f27f))
* add getItems method and change holder type in IPaginatedVoidInventory (#alpha) ([d95fe06](https://github.com/GeorgeV220/VoidChestAPI/commit/d95fe064933611d282f7c98aadf0d540d25932be))
* add IPaginatedVoidInventoryHolder interface ([304c8a7](https://github.com/GeorgeV220/VoidChestAPI/commit/304c8a726c5eca6c68462cd8607abac6e3b4e082))
* add loadAll and saveAll methods to IFilterManager ([81a3af4](https://github.com/GeorgeV220/VoidChestAPI/commit/81a3af445a0d3e7019cc350b30049eb6457f7683))
* add maxPages parameter to createPaginatedInventory methods ([799d856](https://github.com/GeorgeV220/VoidChestAPI/commit/799d856320c2b98470dbaa23bd46b603902279a4))
* Add methods and return values to IPaginatedVoidInventory ([9bca481](https://github.com/GeorgeV220/VoidChestAPI/commit/9bca481a397a21db4db96a93b59d8828feeb1a1c))
* add minY and maxY fields to SerializableLocation ([f698f0d](https://github.com/GeorgeV220/VoidChestAPI/commit/f698f0dce2773233765467a301b3aea2704018a3))
* add NullableArrayList class and use it in VoidInventory ([61524ad](https://github.com/GeorgeV220/VoidChestAPI/commit/61524ad47c4edca7a2749621c346838efac966ae))
* add paginated inventory methods to VoidInventoryUtils (#alpha) ([7943f53](https://github.com/GeorgeV220/VoidChestAPI/commit/7943f535e9793a41f7f114597e78bc99178f6fb1))
* add paginated void inventory API (WIP) ([9db98f1](https://github.com/GeorgeV220/VoidChestAPI/commit/9db98f13b9d479e17a041cfb6a4a43dbe77c95cb))
* add support for multiple void inventories per holder ([0f9d719](https://github.com/GeorgeV220/VoidChestAPI/commit/0f9d71937b2c16ff402cc6d8070cf41a28777034))
* add UnmodifiableArrayList class ([28950ee](https://github.com/GeorgeV220/VoidChestAPI/commit/28950ee77ebbbd3e0cc2376ecb31c4c718a79c15))
* add upgrade manager and upgrade classes for void chests ([aed120f](https://github.com/GeorgeV220/VoidChestAPI/commit/aed120feddd76a1e158a6fdfbd714728cc3562eb))
* add Upgrades interface and Upgrade class for void storage ([8fe93ec](https://github.com/GeorgeV220/VoidChestAPI/commit/8fe93ec6f61e5b0bf1be1fd29a1f5ccacf976beb))
* **api:** remove createInventory method from PaginatedVoidInventoryHolder ([a9975e5](https://github.com/GeorgeV220/VoidChestAPI/commit/a9975e549786a527dc75db4075bca6c4edefcaa9))
* **api:** remove page parameter from addNavigationButton method ([3b34830](https://github.com/GeorgeV220/VoidChestAPI/commit/3b34830484e9bd36f384fcab45c1809527ffdf2b))
* **BoundingBox:** add constructors and methods for center-based bounding boxes ([ea8cfe1](https://github.com/GeorgeV220/VoidChestAPI/commit/ea8cfe1cebbe64e17ba3b546c8dbc339b06a14ec))
* **BoundingBox:** add setters for coordinates ([d8626d3](https://github.com/GeorgeV220/VoidChestAPI/commit/d8626d3156c5b0438254393357878f84663e1ab1))
* **Cancellable:** Deprecate cancel() and add setCancelled(boolean) method ([758e18c](https://github.com/GeorgeV220/VoidChestAPI/commit/758e18c6259da09212fb61bb1b32df909cb58592))
* **event/API:** Add new setCancelled API ([efeaca6](https://github.com/GeorgeV220/VoidChestAPI/commit/efeaca6c8bc70fd287d897c6f0238d284f097a81))
* **IEconomy:** add getSimpleName method ([8914e6b](https://github.com/GeorgeV220/VoidChestAPI/commit/8914e6b1f5463d2810e231c093bcb1db51b20eb8))
* **inventory/API:** Add new createInventory methods ([7ffeecf](https://github.com/GeorgeV220/VoidChestAPI/commit/7ffeecfcd0c5e216fbd962d6d768ac74e5278fce))
* **inventory/API:** Add VoidInventoryUtils class. ([dd7362b](https://github.com/GeorgeV220/VoidChestAPI/commit/dd7362b210e669477a627f8678c20ebe83131937))
* **inventory/Events:** Add new Inventory Events ([093b77f](https://github.com/GeorgeV220/VoidChestAPI/commit/093b77f7b519133146740d94757aba91a785b442))
* **inventory/holder:** Add MenuVoidInventoryHolder and update BlockVoidInventoryHolder and VoidInventoryHolder docs ([f1d185b](https://github.com/GeorgeV220/VoidChestAPI/commit/f1d185be280b3631c93e0cb2ab20d9d41b288562))
* **inventory/InventoryType:** Add FILTERS and mark for removal WHITELIST/BLACKLIST ([6564661](https://github.com/GeorgeV220/VoidChestAPI/commit/656466131c85f2c3639f777b526e5948febbe19f))
* **inventory/IVoidStorage:** Add a method to create a new VoidInventory ([a222013](https://github.com/GeorgeV220/VoidChestAPI/commit/a222013dd7431775a0aa2efdc7cc16f2d9139ddb))
* **inventory/IVoidStorage:** Add a method to get the paginated block inventory (WIP) ([29e984a](https://github.com/GeorgeV220/VoidChestAPI/commit/29e984a277881952b8b1b1989eeb17e9ee459f03))
* **inventory/VoidInventoryItemStack:** Change visibleItemStack to SerializableItemStack to store custom data ([c44f20e](https://github.com/GeorgeV220/VoidChestAPI/commit/c44f20e59321787ad6f7cd6becdb090a3ef7fbd3))
* **inventory/VoidInventory:** Remove ApiStatus.Internal from the update method and update JavaDocs. ([bef9bbc](https://github.com/GeorgeV220/VoidChestAPI/commit/bef9bbc0cec5bee3d5e026205ab16f3bb33bf4da))
* **inventory:** Add BlockVoidInventoryHolder for STORAGE InventoryType ([0e2d3af](https://github.com/GeorgeV220/VoidChestAPI/commit/0e2d3affb628ec1a1f63af665fd60ec31bb4477d))
* **inventory:** add method to add navigation buttons ([34440ef](https://github.com/GeorgeV220/VoidChestAPI/commit/34440eff9b0e7ba5fb06eeee330f5187805af435))
* **inventory:** add PaginatedInventorySwitchPageEvent ([b99d59d](https://github.com/GeorgeV220/VoidChestAPI/commit/b99d59d86d42c43b10670c99e6c290bcac53916c))
* **inventory:** add player-specific pages to paginated void inventory ([8694654](https://github.com/GeorgeV220/VoidChestAPI/commit/869465472f30cbcd9455898cf73708f48c92b44b))
* **inventory:** add voidInventories field to PaginatedVoidInventoryHolder ([44ad665](https://github.com/GeorgeV220/VoidChestAPI/commit/44ad665b775c3028c1d9303d116fc702af47db5c))
* **inventory:** add VoidInventoryCloseEvent ([7f5161c](https://github.com/GeorgeV220/VoidChestAPI/commit/7f5161c38e544961e03c4056f9a9f074db45c84a))
* **IVoidChestBankTNT:** add getSimpleName method ([8dcd950](https://github.com/GeorgeV220/VoidChestAPI/commit/8dcd950baf748ef69767400afe68a390e99e35d1))
* **IVoidChestHologram:** add getName getSimpleName methods ([1532318](https://github.com/GeorgeV220/VoidChestAPI/commit/153231808e8cf890b357cf28bf4df3bd2d630477))
* **IVoidEconomy:** add getSimpleName method ([832e157](https://github.com/GeorgeV220/VoidChestAPI/commit/832e157f634702e45e8795bce80011d4fce4fd2b))
* **IVoidEconomy:** add online player requirement method ([aa05d0e](https://github.com/GeorgeV220/VoidChestAPI/commit/aa05d0e5db0f7a745c691f7fe25a9090ac029a55))
* **IVoidStorage:** add boundingBox method ([972a83b](https://github.com/GeorgeV220/VoidChestAPI/commit/972a83b61fae7c9dc1eb7c23cf283568235a3f56))
* **PaginatedVoidInventoryHolder:** add Iterable interface and null safety ([c9de35d](https://github.com/GeorgeV220/VoidChestAPI/commit/c9de35d4c16c274bda936f348b65efb9380a0091))
* **revert:** use NullableFixedSizeList instead of NullableArrayList ([54769b1](https://github.com/GeorgeV220/VoidChestAPI/commit/54769b17f5a6335c3c392359aeaa171cabeffd7c))
* **SerializableLocation:** implements Cloneable and add clone() method ([5005e11](https://github.com/GeorgeV220/VoidChestAPI/commit/5005e11ad4208d2d95cbd646c2422199d7257deb))
* simplify IPaginatedVoidInventoryHolder interface ([bf6f7c4](https://github.com/GeorgeV220/VoidChestAPI/commit/bf6f7c4b5a88cd663925123b6826b869f3e844e6))
* **Stacker:** add getSimpleName method ([0c8b3b4](https://github.com/GeorgeV220/VoidChestAPI/commit/0c8b3b4602bdf23f200400b99c7b1e3b4117f1bc))
* **storage/Filter:** Add filterIdentifier() method ([6061577](https://github.com/GeorgeV220/VoidChestAPI/commit/606157758a2919d224eb04bc03a679f8a5a7a62b))
* **storage/Filter:** Change item() method from SerializableItemStack to VoidInventoryItemStack ([e8afbca](https://github.com/GeorgeV220/VoidChestAPI/commit/e8afbca3b87c749bedcff92b64ddc07db37d2bc7))
* **storage/Filter:** remove unused imports and methods ([8ac7bbf](https://github.com/GeorgeV220/VoidChestAPI/commit/8ac7bbf7544178b4f4653ba6049034a2422462ac))
* **storage/IFilterManager:** add methods to check if item is allowed for collection ([eefd5e9](https://github.com/GeorgeV220/VoidChestAPI/commit/eefd5e9b64e2917faefd041508e5d948777da363))
* **storage/IVoidStorage:** Introduce Filter class and deprecate blacklistInventory and whitelistInventory methods ([70e4a3e](https://github.com/GeorgeV220/VoidChestAPI/commit/70e4a3e7864bf3db1fdde6886d61499ed9e07090))
* **storage/IVoidStorage:** replace VoidInventory with IPaginatedVoidInventory ([aa3f0d0](https://github.com/GeorgeV220/VoidChestAPI/commit/aa3f0d0105039c40019b3bec34a494bd7f33b635))
* **storage:** add IFilterManager interface and update IVoidStorage docs ([2f0c27a](https://github.com/GeorgeV220/VoidChestAPI/commit/2f0c27a9aec79c618c16e326f48e9b379c3a57f8))
* **storage:** add load and save methods for item filters ([ec0eb3d](https://github.com/GeorgeV220/VoidChestAPI/commit/ec0eb3dca4de19f7a7ad36045df34c22a6fd4dc1))
* **Upgrade:** add item stack handling methods ([04d1d1e](https://github.com/GeorgeV220/VoidChestAPI/commit/04d1d1eacabd969bc1189d438262982eed74e36d))
* **Upgrade:** add player parameter and methods to upgrade object ([e35d9d6](https://github.com/GeorgeV220/VoidChestAPI/commit/e35d9d6db26442bdd72159f1aeae2db036aaec78))
* **Upgrade:** change upgrade method return type to enum ([f419cc1](https://github.com/GeorgeV220/VoidChestAPI/commit/f419cc1b31c484fe9e70ebc5c8d1e3f0324678d2))
* **Upgrade:** change upgrade methods to return boolean ([1c1b730](https://github.com/GeorgeV220/VoidChestAPI/commit/1c1b730d8336e7402ba3860fb627e932d70145a4))
* **Upgrade:** make Upgrade class abstract and add upgrade method (#alpha) ([95c4b74](https://github.com/GeorgeV220/VoidChestAPI/commit/95c4b74e9eb4f5c3514919e215d3597363138811))
* **upgrades:** add getMaxLevel method to Upgrade class ([7e07f17](https://github.com/GeorgeV220/VoidChestAPI/commit/7e07f17037792eef169e925b0081b271aeee380e))
* **Upgrades:** add level and nullability annotations to Upgrade classes ([4adedf9](https://github.com/GeorgeV220/VoidChestAPI/commit/4adedf92dc846d555053bbc627f32c0bc45107a5))
* **Upgrades:** add methods for managing upgrades ([1fb00a3](https://github.com/GeorgeV220/VoidChestAPI/commit/1fb00a32ef3852d659f2dc977da7892211f1bd14))
* **upgrades:** add setters for upgrade object and level ([5b6de42](https://github.com/GeorgeV220/VoidChestAPI/commit/5b6de4267047113cb012b429bc0e72b4efb8ee48))
* **voidstorage/Abilities:** Replace whitelist/blacklist with filters. ([1f4582e](https://github.com/GeorgeV220/VoidChestAPI/commit/1f4582e28bd0b671dca33f3599c774d84c594465))

# [2.2.0-alpha.52](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.51...v2.2.0-alpha.52) (2024-03-22)


### Features

* **IEconomy:** add getSimpleName method ([8914e6b](https://github.com/GeorgeV220/VoidChestAPI/commit/8914e6b1f5463d2810e231c093bcb1db51b20eb8))
* **IVoidChestBankTNT:** add getSimpleName method ([8dcd950](https://github.com/GeorgeV220/VoidChestAPI/commit/8dcd950baf748ef69767400afe68a390e99e35d1))
* **IVoidChestHologram:** add getName getSimpleName methods ([1532318](https://github.com/GeorgeV220/VoidChestAPI/commit/153231808e8cf890b357cf28bf4df3bd2d630477))
* **IVoidEconomy:** add getSimpleName method ([832e157](https://github.com/GeorgeV220/VoidChestAPI/commit/832e157f634702e45e8795bce80011d4fce4fd2b))
* **Stacker:** add getSimpleName method ([0c8b3b4](https://github.com/GeorgeV220/VoidChestAPI/commit/0c8b3b4602bdf23f200400b99c7b1e3b4117f1bc))

# [2.2.0-alpha.51](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.50...v2.2.0-alpha.51) (2024-03-20)


### Features

* **IVoidEconomy:** add online player requirement method ([aa05d0e](https://github.com/GeorgeV220/VoidChestAPI/commit/aa05d0e5db0f7a745c691f7fe25a9090ac029a55))

# [2.2.0-alpha.50](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.49...v2.2.0-alpha.50) (2024-03-20)


### Bug Fixes

* **Upgrade.java:** change return type of upgrade method ([83ddfff](https://github.com/GeorgeV220/VoidChestAPI/commit/83ddfffd8802dd52331b4c53f594ba97a6f45c2f))

# [2.2.0-alpha.49](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.48...v2.2.0-alpha.49) (2024-03-20)


### Features

* **Upgrade:** change upgrade method return type to enum ([f419cc1](https://github.com/GeorgeV220/VoidChestAPI/commit/f419cc1b31c484fe9e70ebc5c8d1e3f0324678d2))

# [2.2.0-alpha.48](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.47...v2.2.0-alpha.48) (2024-03-19)


### Features

* **Upgrade:** change upgrade methods to return boolean ([1c1b730](https://github.com/GeorgeV220/VoidChestAPI/commit/1c1b730d8336e7402ba3860fb627e932d70145a4))

# [2.2.0-alpha.47](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.46...v2.2.0-alpha.47) (2024-03-17)


### Features

* **Upgrade:** add item stack handling methods ([04d1d1e](https://github.com/GeorgeV220/VoidChestAPI/commit/04d1d1eacabd969bc1189d438262982eed74e36d))

# [2.2.0-alpha.46](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.45...v2.2.0-alpha.46) (2024-03-06)


### Features

* **Upgrade:** add player parameter and methods to upgrade object ([e35d9d6](https://github.com/GeorgeV220/VoidChestAPI/commit/e35d9d6db26442bdd72159f1aeae2db036aaec78))

# [2.2.0-alpha.45](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.44...v2.2.0-alpha.45) (2024-03-04)


### Bug Fixes

* **boundingbox:** use absolute values for coordinates ([0512b46](https://github.com/GeorgeV220/VoidChestAPI/commit/0512b46b01afbd36a2cdd9e097eca98c61b57b95))

# [2.2.0-alpha.44](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.43...v2.2.0-alpha.44) (2024-03-04)


### Bug Fixes

* **boundingbox:** use absolute values for coordinates ([a717765](https://github.com/GeorgeV220/VoidChestAPI/commit/a71776554830dc125f9843f2df65ebd9e108ee59))

# [2.2.0-alpha.43](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.42...v2.2.0-alpha.43) (2024-02-29)


### Features

* **BoundingBox:** add constructors and methods for center-based bounding boxes ([ea8cfe1](https://github.com/GeorgeV220/VoidChestAPI/commit/ea8cfe1cebbe64e17ba3b546c8dbc339b06a14ec))

# [2.2.0-alpha.42](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.41...v2.2.0-alpha.42) (2024-02-29)


### Features

* **upgrades:** add setters for upgrade object and level ([5b6de42](https://github.com/GeorgeV220/VoidChestAPI/commit/5b6de4267047113cb012b429bc0e72b4efb8ee48))

# [2.2.0-alpha.41](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.40...v2.2.0-alpha.41) (2024-02-29)


### Features

* **upgrades:** add getMaxLevel method to Upgrade class ([7e07f17](https://github.com/GeorgeV220/VoidChestAPI/commit/7e07f17037792eef169e925b0081b271aeee380e))

# [2.2.0-alpha.40](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.39...v2.2.0-alpha.40) (2024-02-28)


### Features

* **Upgrade:** make Upgrade class abstract and add upgrade method (#alpha) ([95c4b74](https://github.com/GeorgeV220/VoidChestAPI/commit/95c4b74e9eb4f5c3514919e215d3597363138811))

# [2.2.0-alpha.39](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.38...v2.2.0-alpha.39) (2024-02-28)


### Features

* add UnmodifiableArrayList class ([28950ee](https://github.com/GeorgeV220/VoidChestAPI/commit/28950ee77ebbbd3e0cc2376ecb31c4c718a79c15))
* add upgrade manager and upgrade classes for void chests ([aed120f](https://github.com/GeorgeV220/VoidChestAPI/commit/aed120feddd76a1e158a6fdfbd714728cc3562eb))
* **BoundingBox:** add setters for coordinates ([d8626d3](https://github.com/GeorgeV220/VoidChestAPI/commit/d8626d3156c5b0438254393357878f84663e1ab1))

# [2.2.0-alpha.38](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.37...v2.2.0-alpha.38) (2024-02-25)


### Features

* **Upgrades:** add methods for managing upgrades ([1fb00a3](https://github.com/GeorgeV220/VoidChestAPI/commit/1fb00a32ef3852d659f2dc977da7892211f1bd14))

# [2.2.0-alpha.37](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.36...v2.2.0-alpha.37) (2024-02-25)


### Features

* **Upgrades:** add level and nullability annotations to Upgrade classes ([4adedf9](https://github.com/GeorgeV220/VoidChestAPI/commit/4adedf92dc846d555053bbc627f32c0bc45107a5))

# [2.2.0-alpha.36](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.35...v2.2.0-alpha.36) (2024-02-25)


### Features

* add Upgrades interface and Upgrade class for void storage ([8fe93ec](https://github.com/GeorgeV220/VoidChestAPI/commit/8fe93ec6f61e5b0bf1be1fd29a1f5ccacf976beb))

# [2.2.0-alpha.35](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.34...v2.2.0-alpha.35) (2024-02-24)


### Bug Fixes

* **filters:** replace SerializableItemStack with VoidInventoryItemStack ([1fa904b](https://github.com/GeorgeV220/VoidChestAPI/commit/1fa904b46b71dec978e84f1397fe1c8e885cb4a2))

# [2.2.0-alpha.34](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.33...v2.2.0-alpha.34) (2024-02-23)


### Bug Fixes

* **api:** add null check for blockInventory() ([2cb50b1](https://github.com/GeorgeV220/VoidChestAPI/commit/2cb50b16821d00a5e3e2e3777172a237c910341a))

# [2.2.0-alpha.33](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.32...v2.2.0-alpha.33) (2024-02-19)


### Features

* **storage/IVoidStorage:** replace VoidInventory with IPaginatedVoidInventory ([aa3f0d0](https://github.com/GeorgeV220/VoidChestAPI/commit/aa3f0d0105039c40019b3bec34a494bd7f33b635))

# [2.2.0-alpha.32](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.31...v2.2.0-alpha.32) (2024-02-18)


### Features

* **api:** remove page parameter from addNavigationButton method ([3b34830](https://github.com/GeorgeV220/VoidChestAPI/commit/3b34830484e9bd36f384fcab45c1809527ffdf2b))

# [2.2.0-alpha.31](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.30...v2.2.0-alpha.31) (2024-02-18)


### Features

* **inventory:** add method to add navigation buttons ([34440ef](https://github.com/GeorgeV220/VoidChestAPI/commit/34440eff9b0e7ba5fb06eeee330f5187805af435))

# [2.2.0-alpha.30](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.29...v2.2.0-alpha.30) (2024-02-18)


### Features

* add maxPages parameter to createPaginatedInventory methods ([799d856](https://github.com/GeorgeV220/VoidChestAPI/commit/799d856320c2b98470dbaa23bd46b603902279a4))
* **inventory:** add PaginatedInventorySwitchPageEvent ([b99d59d](https://github.com/GeorgeV220/VoidChestAPI/commit/b99d59d86d42c43b10670c99e6c290bcac53916c))
* **inventory:** add VoidInventoryCloseEvent ([7f5161c](https://github.com/GeorgeV220/VoidChestAPI/commit/7f5161c38e544961e03c4056f9a9f074db45c84a))

# [2.2.0-alpha.29](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.28...v2.2.0-alpha.29) (2024-02-17)


### Features

* Add methods and return values to IPaginatedVoidInventory ([9bca481](https://github.com/GeorgeV220/VoidChestAPI/commit/9bca481a397a21db4db96a93b59d8828feeb1a1c))

# [2.2.0-alpha.28](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.27...v2.2.0-alpha.28) (2024-02-12)


### Features

* simplify IPaginatedVoidInventoryHolder interface ([bf6f7c4](https://github.com/GeorgeV220/VoidChestAPI/commit/bf6f7c4b5a88cd663925123b6826b869f3e844e6))

# [2.2.0-alpha.27](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.26...v2.2.0-alpha.27) (2024-02-12)


### Features

* add getItems method and change holder type in IPaginatedVoidInventory (#alpha) ([d95fe06](https://github.com/GeorgeV220/VoidChestAPI/commit/d95fe064933611d282f7c98aadf0d540d25932be))
* add IPaginatedVoidInventoryHolder interface ([304c8a7](https://github.com/GeorgeV220/VoidChestAPI/commit/304c8a726c5eca6c68462cd8607abac6e3b4e082))


### Reverts

* Revert "feat(VoidInventoryItemStack): add index field and getter" ([c6002ec](https://github.com/GeorgeV220/VoidChestAPI/commit/c6002ec0e2eebb14d700ae62ce81b58c265e3bf1))

# [2.2.0-alpha.26](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.25...v2.2.0-alpha.26) (2024-02-12)


### Features

* add and remove methods for paginated void inventory ([42053eb](https://github.com/GeorgeV220/VoidChestAPI/commit/42053ebf596fbf2a1dbc376a8e80baecde65c09f))
* add paginated inventory methods to VoidInventoryUtils (#alpha) ([7943f53](https://github.com/GeorgeV220/VoidChestAPI/commit/7943f535e9793a41f7f114597e78bc99178f6fb1))
* **PaginatedVoidInventoryHolder:** add Iterable interface and null safety ([c9de35d](https://github.com/GeorgeV220/VoidChestAPI/commit/c9de35d4c16c274bda936f348b65efb9380a0091))
* **VoidInventoryItemStack:** add index field and getter ([53728ff](https://github.com/GeorgeV220/VoidChestAPI/commit/53728ff12e957653dbc180572c797976680ce13b))

# [2.2.0-alpha.25](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.24...v2.2.0-alpha.25) (2024-02-11)


### Features

* **inventory:** add player-specific pages to paginated void inventory ([8694654](https://github.com/GeorgeV220/VoidChestAPI/commit/869465472f30cbcd9455898cf73708f48c92b44b))

# [2.2.0-alpha.24](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.23...v2.2.0-alpha.24) (2024-02-11)


### Features

* **api:** remove createInventory method from PaginatedVoidInventoryHolder ([a9975e5](https://github.com/GeorgeV220/VoidChestAPI/commit/a9975e549786a527dc75db4075bca6c4edefcaa9))

# [2.2.0-alpha.23](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.22...v2.2.0-alpha.23) (2024-02-11)


### Features

* **inventory:** add voidInventories field to PaginatedVoidInventoryHolder ([44ad665](https://github.com/GeorgeV220/VoidChestAPI/commit/44ad665b775c3028c1d9303d116fc702af47db5c))

# [2.2.0-alpha.22](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.21...v2.2.0-alpha.22) (2024-02-11)


### Features

* add support for multiple void inventories per holder ([0f9d719](https://github.com/GeorgeV220/VoidChestAPI/commit/0f9d71937b2c16ff402cc6d8070cf41a28777034))

# [2.2.0-alpha.21](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.20...v2.2.0-alpha.21) (2024-02-11)


### Features

* **revert:** use NullableFixedSizeList instead of NullableArrayList ([54769b1](https://github.com/GeorgeV220/VoidChestAPI/commit/54769b17f5a6335c3c392359aeaa171cabeffd7c))

# [2.2.0-alpha.20](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.19...v2.2.0-alpha.20) (2024-02-10)


### Features

* add NullableArrayList class and use it in VoidInventory ([61524ad](https://github.com/GeorgeV220/VoidChestAPI/commit/61524ad47c4edca7a2749621c346838efac966ae))

# [2.2.0-alpha.19](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.18...v2.2.0-alpha.19) (2024-02-10)


### Features

* **storage/Filter:** remove unused imports and methods ([8ac7bbf](https://github.com/GeorgeV220/VoidChestAPI/commit/8ac7bbf7544178b4f4653ba6049034a2422462ac))

# [2.2.0-alpha.18](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.17...v2.2.0-alpha.18) (2024-02-10)


### Features

* **storage:** add load and save methods for item filters ([ec0eb3d](https://github.com/GeorgeV220/VoidChestAPI/commit/ec0eb3dca4de19f7a7ad36045df34c22a6fd4dc1))

# [2.2.0-alpha.17](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.16...v2.2.0-alpha.17) (2024-02-10)


### Features

* add loadAll and saveAll methods to IFilterManager ([81a3af4](https://github.com/GeorgeV220/VoidChestAPI/commit/81a3af445a0d3e7019cc350b30049eb6457f7683))

# [2.2.0-alpha.16](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.15...v2.2.0-alpha.16) (2024-02-10)


### Features

* add paginated void inventory API (WIP) ([9db98f1](https://github.com/GeorgeV220/VoidChestAPI/commit/9db98f13b9d479e17a041cfb6a4a43dbe77c95cb))

# [2.2.0-alpha.15](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.14...v2.2.0-alpha.15) (2024-02-10)


### Features

* **storage/IFilterManager:** add methods to check if item is allowed for collection ([eefd5e9](https://github.com/GeorgeV220/VoidChestAPI/commit/eefd5e9b64e2917faefd041508e5d948777da363))

# [2.2.0-alpha.14](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.13...v2.2.0-alpha.14) (2024-02-10)


### Features

* add filter manager to VoidChestAPI ([39d4554](https://github.com/GeorgeV220/VoidChestAPI/commit/39d45540f74467e485348a76796942708b23f27f))

# [2.2.0-alpha.13](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.12...v2.2.0-alpha.13) (2024-02-10)


### Features

* **storage:** add IFilterManager interface and update IVoidStorage docs ([2f0c27a](https://github.com/GeorgeV220/VoidChestAPI/commit/2f0c27a9aec79c618c16e326f48e9b379c3a57f8))

# [2.2.0-alpha.12](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.11...v2.2.0-alpha.12) (2024-02-08)


### Features

* **inventory/API:** Add new createInventory methods ([7ffeecf](https://github.com/GeorgeV220/VoidChestAPI/commit/7ffeecfcd0c5e216fbd962d6d768ac74e5278fce))

# [2.2.0-alpha.11](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.10...v2.2.0-alpha.11) (2024-02-08)


### Features

* **Cancellable:** Deprecate cancel() and add setCancelled(boolean) method ([758e18c](https://github.com/GeorgeV220/VoidChestAPI/commit/758e18c6259da09212fb61bb1b32df909cb58592))
* **event/API:** Add new setCancelled API ([efeaca6](https://github.com/GeorgeV220/VoidChestAPI/commit/efeaca6c8bc70fd287d897c6f0238d284f097a81))
* **inventory/API:** Add VoidInventoryUtils class. ([dd7362b](https://github.com/GeorgeV220/VoidChestAPI/commit/dd7362b210e669477a627f8678c20ebe83131937))
* **inventory/Events:** Add new Inventory Events ([093b77f](https://github.com/GeorgeV220/VoidChestAPI/commit/093b77f7b519133146740d94757aba91a785b442))

# [2.2.0-alpha.10](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.9...v2.2.0-alpha.10) (2024-02-07)


### Features

* **voidstorage/Abilities:** Replace whitelist/blacklist with filters. ([1f4582e](https://github.com/GeorgeV220/VoidChestAPI/commit/1f4582e28bd0b671dca33f3599c774d84c594465))

# [2.2.0-alpha.9](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.8...v2.2.0-alpha.9) (2024-02-06)


### Features

* **inventory/VoidInventoryItemStack:** Change visibleItemStack to SerializableItemStack to store custom data ([c44f20e](https://github.com/GeorgeV220/VoidChestAPI/commit/c44f20e59321787ad6f7cd6becdb090a3ef7fbd3))
* **storage/Filter:** Change item() method from SerializableItemStack to VoidInventoryItemStack ([e8afbca](https://github.com/GeorgeV220/VoidChestAPI/commit/e8afbca3b87c749bedcff92b64ddc07db37d2bc7))

# [2.2.0-alpha.8](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.7...v2.2.0-alpha.8) (2024-02-06)


### Features

* **storage/Filter:** Add filterIdentifier() method ([6061577](https://github.com/GeorgeV220/VoidChestAPI/commit/606157758a2919d224eb04bc03a679f8a5a7a62b))

# [2.2.0-alpha.7](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.6...v2.2.0-alpha.7) (2024-02-06)


### Features

* **inventory/IVoidStorage:** Add a method to create a new VoidInventory ([a222013](https://github.com/GeorgeV220/VoidChestAPI/commit/a222013dd7431775a0aa2efdc7cc16f2d9139ddb))
* **inventory/IVoidStorage:** Add a method to get the paginated block inventory (WIP) ([29e984a](https://github.com/GeorgeV220/VoidChestAPI/commit/29e984a277881952b8b1b1989eeb17e9ee459f03))

# [2.2.0-alpha.6](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.5...v2.2.0-alpha.6) (2024-02-06)


### Features

* **inventory/holder:** Add MenuVoidInventoryHolder and update BlockVoidInventoryHolder and VoidInventoryHolder docs ([f1d185b](https://github.com/GeorgeV220/VoidChestAPI/commit/f1d185be280b3631c93e0cb2ab20d9d41b288562))
* **inventory/InventoryType:** Add FILTERS and mark for removal WHITELIST/BLACKLIST ([6564661](https://github.com/GeorgeV220/VoidChestAPI/commit/656466131c85f2c3639f777b526e5948febbe19f))
* **inventory/VoidInventory:** Remove ApiStatus.Internal from the update method and update JavaDocs. ([bef9bbc](https://github.com/GeorgeV220/VoidChestAPI/commit/bef9bbc0cec5bee3d5e026205ab16f3bb33bf4da))

# [2.2.0-alpha.5](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.4...v2.2.0-alpha.5) (2024-02-06)


### Bug Fixes

* **storage/IVoidStorage:** Update JavaDocs and mark white/blacklist inventories forRemoval ([49c0474](https://github.com/GeorgeV220/VoidChestAPI/commit/49c047487ac088ffd77d8e755611a3d31b527f91))

# [2.2.0-alpha.4](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.3...v2.2.0-alpha.4) (2024-02-06)


### Bug Fixes

* **VoidChestAPI:** Update the library version and add Entity to Minecraft Scheduler ([4e0382d](https://github.com/GeorgeV220/VoidChestAPI/commit/4e0382d3e97261456241e236fb41985bbcb4f607))

# [2.2.0-alpha.3](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.2...v2.2.0-alpha.3) (2024-02-06)


### Bug Fixes

* **BoundingBox:** Change clone method to public ([4b82ed6](https://github.com/GeorgeV220/VoidChestAPI/commit/4b82ed612147f603dca146287b8cfac7d017bb3d))


### Features

* **SerializableLocation:** implements Cloneable and add clone() method ([5005e11](https://github.com/GeorgeV220/VoidChestAPI/commit/5005e11ad4208d2d95cbd646c2422199d7257deb))
* **storage/IVoidStorage:** Introduce Filter class and deprecate blacklistInventory and whitelistInventory methods ([70e4a3e](https://github.com/GeorgeV220/VoidChestAPI/commit/70e4a3e7864bf3db1fdde6886d61499ed9e07090))

# [2.2.0-alpha.2](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.2.0-alpha.1...v2.2.0-alpha.2) (2024-02-05)


### Features

* add BoundingBox class for 3D space representation ([c60231d](https://github.com/GeorgeV220/VoidChestAPI/commit/c60231d0f16c192d2a8b0ff01c61c6ed25c361ad))
* add minY and maxY fields to SerializableLocation ([f698f0d](https://github.com/GeorgeV220/VoidChestAPI/commit/f698f0dce2773233765467a301b3aea2704018a3))
* **IVoidStorage:** add boundingBox method ([972a83b](https://github.com/GeorgeV220/VoidChestAPI/commit/972a83b61fae7c9dc1eb7c23cf283568235a3f56))

# [2.2.0-alpha.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.1.0...v2.2.0-alpha.1) (2024-02-04)


### Bug Fixes

* **SerializableLocation:** change toLocation() to return a clone of the location to prevent changes to the original object ([e86a9a2](https://github.com/GeorgeV220/VoidChestAPI/commit/e86a9a2def87a1ebf0e8f7dbc5eaf5da32a47386))


### Features

* **inventory:** Add BlockVoidInventoryHolder for STORAGE InventoryType ([0e2d3af](https://github.com/GeorgeV220/VoidChestAPI/commit/0e2d3affb628ec1a1f63af665fd60ec31bb4477d))

# [2.1.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0...v2.1.0) (2024-01-17)


### Features

* **api:** add debug mode to VoidChestAPI ([ea20fec](https://github.com/GeorgeV220/VoidChestAPI/commit/ea20fec15e38c8bfbfc7bc6ed0901a2a6952ced3))

# [2.0.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.20.0...v2.0.0) (2024-01-16)


### Bug Fixes

* **Boosters:** adjust booster calculations ([a80ba93](https://github.com/GeorgeV220/VoidChestAPI/commit/a80ba9366a2fb4def633e7aef42057e33e2ceb93))
* **build:** Do not shade ASM into the API ([54c532b](https://github.com/GeorgeV220/VoidChestAPI/commit/54c532b10465869a862aec8f29144a5a5d22a63b))
* handle edge cases in booster and boostTime calculations ([bfe66bb](https://github.com/GeorgeV220/VoidChestAPI/commit/bfe66bbfce5a5d5f1d3949c6e5fe0e3544d0c1b9))
* **IVoidStorageManager:** Fix getLocationCache and getBlockCache wrong return type ([5790ad5](https://github.com/GeorgeV220/VoidChestAPI/commit/5790ad5f12b8980bf6b44e4c6c2e3a099986e8e3))
* **SerializableLocation:** Make 'toLocation()' method return null if the world is null/not loaded. ([c483d9e](https://github.com/GeorgeV220/VoidChestAPI/commit/c483d9eb504a4ed12cfdd168752b2c42494b1d0f))
* **storage:** Add custom data methods ([bb59573](https://github.com/GeorgeV220/VoidChestAPI/commit/bb595732b359f1cd29e28b7aff5873fdc51749bf))
* **Utilities:** return false in isChunkLoaded when location or world is null ([c39147d](https://github.com/GeorgeV220/VoidChestAPI/commit/c39147da901e9c7b25b8fea891d1df845fefdeb8))


### Code Refactoring

* remove deprecated sell handler from IPlayerData (2.4.0) ([7ffa86b](https://github.com/GeorgeV220/VoidChestAPI/commit/7ffa86b5e5bf31653757d73ac7516d1d91cb865b))


### Features

* **api:** add BoosterEvent class ([895f81d](https://github.com/GeorgeV220/VoidChestAPI/commit/895f81da91e26f0a15a47e6c07db76aabb9a6641))
* **ASM:** Use ASM instead of reflection. (Paper patch) ([6fa3810](https://github.com/GeorgeV220/VoidChestAPI/commit/6fa3810876f1f1894af0fffca7d406194a724de6))
* **Booster:** Add isBoosterActive(Plugin) and getBoosters() methods ([ab1237b](https://github.com/GeorgeV220/VoidChestAPI/commit/ab1237b005be75cad9fcea17a752f79830905dc4))
* **Booster:** add plugin support for booster methods (2.4.0) ([468c2d0](https://github.com/GeorgeV220/VoidChestAPI/commit/468c2d0c1d2b8bb807e5f5cbe0e2076f4b3710ac))
* Change Booster plugin methods to take String instead of a Plugin object. ([0537ee8](https://github.com/GeorgeV220/VoidChestAPI/commit/0537ee843f5a69b7a4cc3d73236e7aa97e34d1a3))
* Introduce "Boosters" class and replace IPlayerData.boosters() List<Booster> with Boosters ([85fb9d8](https://github.com/GeorgeV220/VoidChestAPI/commit/85fb9d81b2864085ae596aac283a6bd1d00d3ddc))
* introduce booster events in Boosters class ([b82a4e9](https://github.com/GeorgeV220/VoidChestAPI/commit/b82a4e9101e43b80f8e595f0fa23eb0242ed1ecb))
* **IPlayerData:** add List<Booster> boosters() method and mark Booster booster() as deprecated ([2348a03](https://github.com/GeorgeV220/VoidChestAPI/commit/2348a03393fe487f551a2f257c240f857e6c1295))
* **IPlayerManager:** Add IPlayerManager.getPlaceHolders(IPlayerData) method ([45bdf96](https://github.com/GeorgeV220/VoidChestAPI/commit/45bdf96d8ae47170a4e6f1fc06069333eddb9f7e))
* **IPlayerManager:** add playerData(UUID) and playerData(UUID, boolean) methods to retrieve IPlayerData objects ([2bdbedd](https://github.com/GeorgeV220/VoidChestAPI/commit/2bdbedd7b1814bcb9de48ef52f3bd389e61ab49b))
* **IVoidStorageManager:** Add cache methods. ([4f517d0](https://github.com/GeorgeV220/VoidChestAPI/commit/4f517d067ac19638002476ceece5f27e7aecdeb3))
* **IVoidStorageManager:** Add voidStorages(Chunk) and mark voidStorage(Chunk) as deprecated. ([01b3a1b](https://github.com/GeorgeV220/VoidChestAPI/commit/01b3a1b8c3478df68b9615d665d635c0146d2f29))
* mark old Booster methods are deprecated for removal. ([4b4e3b6](https://github.com/GeorgeV220/VoidChestAPI/commit/4b4e3b6089fadb8d3b377142646e7f5f091e168e))
* **NullableFixedSizeList:** add isEmpty method ([3be799a](https://github.com/GeorgeV220/VoidChestAPI/commit/3be799adecd8d19898dfe985468d45dec7a8a7bc))
* **player:** introduce HashObjectMap for booster storage ([7ae6b94](https://github.com/GeorgeV220/VoidChestAPI/commit/7ae6b9475442a2eeed96c0efd9bc0d95386ce6a1))
* Removed whitelist/blacklist item methods from Abilities ([f3b6200](https://github.com/GeorgeV220/VoidChestAPI/commit/f3b6200887d1ddc976cc1d212c71647716db2f10))
* **SerializableLocation:** Add new constructor and getters. ([13efdb3](https://github.com/GeorgeV220/VoidChestAPI/commit/13efdb3f8920f25b22c76d6fb867c02fe8dc8d96))
* **storage:** Add playerUUID to IPlayerData and storageUUID to IVoidStorage ([0431261](https://github.com/GeorgeV220/VoidChestAPI/commit/0431261777842ecbf383e3756ee1e4eb61235587))
* **storage:** Do not use Entity and EntityManager interfaces. ([0e5437e](https://github.com/GeorgeV220/VoidChestAPI/commit/0e5437e91e400bb592640935dfccf3faf62dc42b))
* **tasks:** add SellHandler interface for void chest entities (2.4.0) ([2b419f4](https://github.com/GeorgeV220/VoidChestAPI/commit/2b419f44c7f6b2a994738a7a0c32827f9fcb67b6))
* use ExecutorService for async event handling (alpha) ([00460ba](https://github.com/GeorgeV220/VoidChestAPI/commit/00460bacb1597251bbe18cb6dbecd62b4eac9364))
* **VoidChestAPI:** add SellHandler parameter to constructor (2.4.0) ([bffbc94](https://github.com/GeorgeV220/VoidChestAPI/commit/bffbc942ef1b7fffb16afe3fdd9ee815e0d73c59))


### Reverts

* **Booster:** Remove plugin methods from Booster and add pluginIdentifier() (2.4.0) ([e487165](https://github.com/GeorgeV220/VoidChestAPI/commit/e487165e5286efa6516b850aab8df0ea44df0934))


### BREAKING CHANGES

* The sell handler is no longer part of the IPlayerData interface and will be moved to a different class.

# [2.0.0-alpha.21](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.20...v2.0.0-alpha.21) (2024-01-12)


### Bug Fixes

* **Boosters:** adjust booster calculations ([a80ba93](https://github.com/GeorgeV220/VoidChestAPI/commit/a80ba9366a2fb4def633e7aef42057e33e2ceb93))

# [2.0.0-alpha.20](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.19...v2.0.0-alpha.20) (2024-01-12)


### Bug Fixes

* handle edge cases in booster and boostTime calculations ([bfe66bb](https://github.com/GeorgeV220/VoidChestAPI/commit/bfe66bbfce5a5d5f1d3949c6e5fe0e3544d0c1b9))

# [2.0.0-alpha.19](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.18...v2.0.0-alpha.19) (2024-01-12)


### Features

* introduce booster events in Boosters class ([b82a4e9](https://github.com/GeorgeV220/VoidChestAPI/commit/b82a4e9101e43b80f8e595f0fa23eb0242ed1ecb))

# [2.0.0-alpha.18](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.17...v2.0.0-alpha.18) (2024-01-10)


### Features

* **player:** introduce HashObjectMap for booster storage ([7ae6b94](https://github.com/GeorgeV220/VoidChestAPI/commit/7ae6b9475442a2eeed96c0efd9bc0d95386ce6a1))

# [2.0.0-alpha.17](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.16...v2.0.0-alpha.17) (2024-01-10)


### Features

* **api:** add BoosterEvent class ([895f81d](https://github.com/GeorgeV220/VoidChestAPI/commit/895f81da91e26f0a15a47e6c07db76aabb9a6641))

# [2.0.0-alpha.16](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.15...v2.0.0-alpha.16) (2024-01-10)


### Features

* Introduce "Boosters" class and replace IPlayerData.boosters() List<Booster> with Boosters ([85fb9d8](https://github.com/GeorgeV220/VoidChestAPI/commit/85fb9d81b2864085ae596aac283a6bd1d00d3ddc))

# [2.0.0-alpha.15](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.14...v2.0.0-alpha.15) (2024-01-10)


### Features

* **IPlayerManager:** add playerData(UUID) and playerData(UUID, boolean) methods to retrieve IPlayerData objects ([2bdbedd](https://github.com/GeorgeV220/VoidChestAPI/commit/2bdbedd7b1814bcb9de48ef52f3bd389e61ab49b))

# [2.0.0-alpha.14](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.13...v2.0.0-alpha.14) (2024-01-09)


### Bug Fixes

* **IVoidStorageManager:** Fix getLocationCache and getBlockCache wrong return type ([5790ad5](https://github.com/GeorgeV220/VoidChestAPI/commit/5790ad5f12b8980bf6b44e4c6c2e3a099986e8e3))

# [2.0.0-alpha.13](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.12...v2.0.0-alpha.13) (2024-01-09)


### Bug Fixes

* **storage:** Add custom data methods ([bb59573](https://github.com/GeorgeV220/VoidChestAPI/commit/bb595732b359f1cd29e28b7aff5873fdc51749bf))

# [2.0.0-alpha.12](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.11...v2.0.0-alpha.12) (2024-01-09)


### Features

* **storage:** Add playerUUID to IPlayerData and storageUUID to IVoidStorage ([0431261](https://github.com/GeorgeV220/VoidChestAPI/commit/0431261777842ecbf383e3756ee1e4eb61235587))

# [2.0.0-alpha.11](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.10...v2.0.0-alpha.11) (2024-01-09)


### Bug Fixes

* **build:** Do not shade ASM into the API ([54c532b](https://github.com/GeorgeV220/VoidChestAPI/commit/54c532b10465869a862aec8f29144a5a5d22a63b))

# [2.0.0-alpha.10](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.9...v2.0.0-alpha.10) (2024-01-09)


### Bug Fixes

* **Utilities:** return false in isChunkLoaded when location or world is null ([c39147d](https://github.com/GeorgeV220/VoidChestAPI/commit/c39147da901e9c7b25b8fea891d1df845fefdeb8))


### Features

* **ASM:** Use ASM instead of reflection. (Paper patch) ([6fa3810](https://github.com/GeorgeV220/VoidChestAPI/commit/6fa3810876f1f1894af0fffca7d406194a724de6))
* **IVoidStorageManager:** Add cache methods. ([4f517d0](https://github.com/GeorgeV220/VoidChestAPI/commit/4f517d067ac19638002476ceece5f27e7aecdeb3))
* **storage:** Do not use Entity and EntityManager interfaces. ([0e5437e](https://github.com/GeorgeV220/VoidChestAPI/commit/0e5437e91e400bb592640935dfccf3faf62dc42b))

# [2.0.0-alpha.9](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.8...v2.0.0-alpha.9) (2024-01-09)


### Features

* **IPlayerData:** add List<Booster> boosters() method and mark Booster booster() as deprecated ([2348a03](https://github.com/GeorgeV220/VoidChestAPI/commit/2348a03393fe487f551a2f257c240f857e6c1295))
* **IVoidStorageManager:** Add voidStorages(Chunk) and mark voidStorage(Chunk) as deprecated. ([01b3a1b](https://github.com/GeorgeV220/VoidChestAPI/commit/01b3a1b8c3478df68b9615d665d635c0146d2f29))


### Reverts

* **Booster:** Remove plugin methods from Booster and add pluginIdentifier() (2.4.0) ([e487165](https://github.com/GeorgeV220/VoidChestAPI/commit/e487165e5286efa6516b850aab8df0ea44df0934))

# [2.0.0-alpha.8](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.7...v2.0.0-alpha.8) (2024-01-08)


### Features

* **IPlayerManager:** Add IPlayerManager.getPlaceHolders(IPlayerData) method ([45bdf96](https://github.com/GeorgeV220/VoidChestAPI/commit/45bdf96d8ae47170a4e6f1fc06069333eddb9f7e))

# [2.0.0-alpha.7](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.6...v2.0.0-alpha.7) (2024-01-08)


### Features

* mark old Booster methods are deprecated for removal. ([4b4e3b6](https://github.com/GeorgeV220/VoidChestAPI/commit/4b4e3b6089fadb8d3b377142646e7f5f091e168e))

# [2.0.0-alpha.6](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.5...v2.0.0-alpha.6) (2024-01-08)


### Features

* Change Booster plugin methods to take String instead of a Plugin object. ([0537ee8](https://github.com/GeorgeV220/VoidChestAPI/commit/0537ee843f5a69b7a4cc3d73236e7aa97e34d1a3))

# [2.0.0-alpha.5](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.4...v2.0.0-alpha.5) (2024-01-08)


### Bug Fixes

* **SerializableLocation:** Make 'toLocation()' method return null if the world is null/not loaded. ([c483d9e](https://github.com/GeorgeV220/VoidChestAPI/commit/c483d9eb504a4ed12cfdd168752b2c42494b1d0f))

# [2.0.0-alpha.4](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.3...v2.0.0-alpha.4) (2024-01-08)


### Features

* **Booster:** Add isBoosterActive(Plugin) and getBoosters() methods ([ab1237b](https://github.com/GeorgeV220/VoidChestAPI/commit/ab1237b005be75cad9fcea17a752f79830905dc4))
* **SerializableLocation:** Add new constructor and getters. ([13efdb3](https://github.com/GeorgeV220/VoidChestAPI/commit/13efdb3f8920f25b22c76d6fb867c02fe8dc8d96))

# [2.0.0-alpha.3](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.2...v2.0.0-alpha.3) (2024-01-07)


### Features

* **NullableFixedSizeList:** add isEmpty method ([3be799a](https://github.com/GeorgeV220/VoidChestAPI/commit/3be799adecd8d19898dfe985468d45dec7a8a7bc))

# [2.0.0-alpha.2](https://github.com/GeorgeV220/VoidChestAPI/compare/v2.0.0-alpha.1...v2.0.0-alpha.2) (2024-01-07)


### Features

* **VoidChestAPI:** add SellHandler parameter to constructor (2.4.0) ([bffbc94](https://github.com/GeorgeV220/VoidChestAPI/commit/bffbc942ef1b7fffb16afe3fdd9ee815e0d73c59))

# [2.0.0-alpha.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.21.0-alpha.1...v2.0.0-alpha.1) (2024-01-07)


### Code Refactoring

* remove deprecated sell handler from IPlayerData (2.4.0) ([7ffa86b](https://github.com/GeorgeV220/VoidChestAPI/commit/7ffa86b5e5bf31653757d73ac7516d1d91cb865b))


### Features

* **Booster:** add plugin support for booster methods (2.4.0) ([468c2d0](https://github.com/GeorgeV220/VoidChestAPI/commit/468c2d0c1d2b8bb807e5f5cbe0e2076f4b3710ac))
* Removed whitelist/blacklist item methods from Abilities ([f3b6200](https://github.com/GeorgeV220/VoidChestAPI/commit/f3b6200887d1ddc976cc1d212c71647716db2f10))
* **tasks:** add SellHandler interface for void chest entities (2.4.0) ([2b419f4](https://github.com/GeorgeV220/VoidChestAPI/commit/2b419f44c7f6b2a994738a7a0c32827f9fcb67b6))


### BREAKING CHANGES

* The sell handler is no longer part of the IPlayerData interface and will be moved to a different class.

# [1.21.0-alpha.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.20.0...v1.21.0-alpha.1) (2024-01-06)


### Features

* use ExecutorService for async event handling (alpha) ([00460ba](https://github.com/GeorgeV220/VoidChestAPI/commit/00460bacb1597251bbe18cb6dbecd62b4eac9364))

# [1.20.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.19.0...v1.20.0) (2024-01-05)


### Features

* add Bukkit imports and update library version (2.4.0) ([f7112fa](https://github.com/GeorgeV220/VoidChestAPI/commit/f7112fa1061e8c13229311b853ddf3414b938f48))

# [1.19.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0...v1.19.0) (2023-12-27)


### Features

* replace Task with SchedulerTask in SellHandler (VoidChest 2.4.0) ([1f2c8b6](https://github.com/GeorgeV220/VoidChestAPI/commit/1f2c8b68c4e158f21ada2da5f8e8711228f43eb6))

# [1.18.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.17.0...v1.18.0) (2023-12-21)


### Bug Fixes

* Change VoidChestAPI IShopManager to shopManager ([168b4fa](https://github.com/GeorgeV220/VoidChestAPI/commit/168b4fa765de4b9dc58f771d1041c3d97f1b56ad))
* Mark IVoidStorage blockInventoryItemStacks for removal ([be4684b](https://github.com/GeorgeV220/VoidChestAPI/commit/be4684b11850fd3558093fddf2bb27b40b2f4c64))
* **NullableFixedSizeList:** Fixed clear method ([dee3c24](https://github.com/GeorgeV220/VoidChestAPI/commit/dee3c240671681793d90a045d3b78fa5bf8bf681))
* **SerializableItemStack:** NBT (de)serialization ([6d180fd](https://github.com/GeorgeV220/VoidChestAPI/commit/6d180fd1fdd67b3d20435c57ab440538f33dadad))
* **VoidInventory:** modify the update methods ([0ade079](https://github.com/GeorgeV220/VoidChestAPI/commit/0ade0796f3bccfeb10329c3bf08c1ee9cb95e9b6))


### Features

* Added InventoryHandler and changed VoidInventory to interface ([6f6eb50](https://github.com/GeorgeV220/VoidChestAPI/commit/6f6eb50171b69aa490f3b8b3f33d3eed61b71c51))
* Added JavaDocs to inventory classes and new methods to VoidInventory ([a415405](https://github.com/GeorgeV220/VoidChestAPI/commit/a415405d57093fa85570b4d41ab17f39963339d2))
* Added NullableFixedSizeList(Integer, Collection) constructor to NullableFixedSizeList ([2e8fa38](https://github.com/GeorgeV220/VoidChestAPI/commit/2e8fa384f7cefea810d3dd385f7ec8a4c505c09f))
* Added NullablePreFilledArrayList ([6452271](https://github.com/GeorgeV220/VoidChestAPI/commit/64522718366301251f8424488384d4732d4fd04a))
* Added VoidInventory ([050f188](https://github.com/GeorgeV220/VoidChestAPI/commit/050f188f721bc8f50acc2a6fd19030c9d37b5083))
* **IEconomyManager:** Added IEconomyManager.isHooked ([62336a1](https://github.com/GeorgeV220/VoidChestAPI/commit/62336a1c458beed9842fd466fe57ab46d5dbfdc8))
* **Inventory:** Refactor Inventory classes, removed SerializableInventory and added VoidInventoryItemStack and InventoryHandler ([c709f4c](https://github.com/GeorgeV220/VoidChestAPI/commit/c709f4ceb536de13c7c63215525952159353bd50))
* **IPlayerData:** Added balance method. ([bd0e119](https://github.com/GeorgeV220/VoidChestAPI/commit/bd0e1196fa28ceebe73109210689d0384d05a5c9))
* **IPlayerData:** Deprecation of IPlayerData.sellHandler ([fe2dbf9](https://github.com/GeorgeV220/VoidChestAPI/commit/fe2dbf9db1e91c74f2e258d9560b1d1c2c7c621c))
* **IVoidEconomyManager:** Removed IVoidEconomyManager.hookVoidEconomy ([15aa05f](https://github.com/GeorgeV220/VoidChestAPI/commit/15aa05f461495e6e73bce3e5d34f373c66489688))
* **IVoidEconomy:** Removed isVaultDependent ([04ea461](https://github.com/GeorgeV220/VoidChestAPI/commit/04ea461131c3b465f6ece2dc1fe333cf7a8c537b))
* **IVoidStorage:** Added blockInventory method() ([bcc2f5f](https://github.com/GeorgeV220/VoidChestAPI/commit/bcc2f5fc7701fdc6589cefd82cb83a705ef2ff7e))
* **IVoidStorageManager:** Added voidStorage(SerializableBlock) voidStorage(Location) and voidStorage(SerializableLocation) ([1a62155](https://github.com/GeorgeV220/VoidChestAPI/commit/1a6215524a674da9a43f9b750b1cabba5e4db7f8))
* **IVoidStorage:** Removed location() and @ApiStatus.Experimental from blockInventory() ([866c978](https://github.com/GeorgeV220/VoidChestAPI/commit/866c9787a5e661ec41f1ef2a38ca6e850208c3df))
* **IVoidStorage:** Removed whitelistInventoryItemStacks blacklistInventoryItemStacks and blockInventoryItemStacks ([0a25186](https://github.com/GeorgeV220/VoidChestAPI/commit/0a2518686b7399d821926ec2f5d126a9f56ea4a3))
* **Options:** Added OPTIONS_INFINITE_ITEM_LORE and OPTIONS_SHOP_ITEM_LORE to VoidChestOptionsUtil ([728e03e](https://github.com/GeorgeV220/VoidChestAPI/commit/728e03ef219fedf4b3c68b17f93585e57eeecefa))
* Removed unused InventoryManager ([03cb843](https://github.com/GeorgeV220/VoidChestAPI/commit/03cb843d65827da10476dd7eabd25403bc91da84))
* Rename ShopManager to IShopManager ([4712ead](https://github.com/GeorgeV220/VoidChestAPI/commit/4712eadda114dd5c8cf10f7a1a12bdc685d886da))
* **revert/VoidInventory:** remove unnecessary methods ([e69dfb7](https://github.com/GeorgeV220/VoidChestAPI/commit/e69dfb7770c0a25628dc5e1f5b147e3a269265b2))
* **VoidInventory:** Added addItem(boolean, ItemStack...) ([1f07ad7](https://github.com/GeorgeV220/VoidChestAPI/commit/1f07ad7bb63bf4ff92ca751d9f48fff3289c6199))
* **VoidInventory:** Added firstPartial ([c68d317](https://github.com/GeorgeV220/VoidChestAPI/commit/c68d317e8e14aa6ba8bd0e3a30b7e0345b499fa0))
* **VoidInventory:** Added internal open(Player) method ([429b9e1](https://github.com/GeorgeV220/VoidChestAPI/commit/429b9e156d694f575bd3097864473a0f4d423296))
* **VoidInventory:** Added update methods ([9e08dbe](https://github.com/GeorgeV220/VoidChestAPI/commit/9e08dbe85fe4b5a388853d00824b07663e0b1846))

# [1.18.0-alpha.25](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.24...v1.18.0-alpha.25) (2023-12-21)


### Features

* **IVoidStorage:** Removed location() and @ApiStatus.Experimental from blockInventory() ([866c978](https://github.com/GeorgeV220/VoidChestAPI/commit/866c9787a5e661ec41f1ef2a38ca6e850208c3df))

# [1.18.0-alpha.24](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.23...v1.18.0-alpha.24) (2023-12-21)


### Features

* **IVoidEconomy:** Removed isVaultDependent ([04ea461](https://github.com/GeorgeV220/VoidChestAPI/commit/04ea461131c3b465f6ece2dc1fe333cf7a8c537b))

# [1.18.0-alpha.23](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.22...v1.18.0-alpha.23) (2023-12-20)


### Features

* **IEconomyManager:** Added IEconomyManager.isHooked ([62336a1](https://github.com/GeorgeV220/VoidChestAPI/commit/62336a1c458beed9842fd466fe57ab46d5dbfdc8))
* **IVoidEconomyManager:** Removed IVoidEconomyManager.hookVoidEconomy ([15aa05f](https://github.com/GeorgeV220/VoidChestAPI/commit/15aa05f461495e6e73bce3e5d34f373c66489688))

# [1.18.0-alpha.22](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.21...v1.18.0-alpha.22) (2023-12-20)


### Features

* **IPlayerData:** Added balance method. ([bd0e119](https://github.com/GeorgeV220/VoidChestAPI/commit/bd0e1196fa28ceebe73109210689d0384d05a5c9))
* **IPlayerData:** Deprecation of IPlayerData.sellHandler ([fe2dbf9](https://github.com/GeorgeV220/VoidChestAPI/commit/fe2dbf9db1e91c74f2e258d9560b1d1c2c7c621c))

# [1.18.0-alpha.21](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.20...v1.18.0-alpha.21) (2023-12-18)


### Bug Fixes

* Change VoidChestAPI IShopManager to shopManager ([168b4fa](https://github.com/GeorgeV220/VoidChestAPI/commit/168b4fa765de4b9dc58f771d1041c3d97f1b56ad))

# [1.18.0-alpha.20](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.19...v1.18.0-alpha.20) (2023-12-17)


### Features

* **revert/VoidInventory:** remove unnecessary methods ([e69dfb7](https://github.com/GeorgeV220/VoidChestAPI/commit/e69dfb7770c0a25628dc5e1f5b147e3a269265b2))

# [1.18.0-alpha.19](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.18...v1.18.0-alpha.19) (2023-12-14)


### Features

* **VoidInventory:** Added firstPartial ([c68d317](https://github.com/GeorgeV220/VoidChestAPI/commit/c68d317e8e14aa6ba8bd0e3a30b7e0345b499fa0))

# [1.18.0-alpha.18](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.17...v1.18.0-alpha.18) (2023-12-14)


### Features

* **VoidInventory:** Added addItem(boolean, ItemStack...) ([1f07ad7](https://github.com/GeorgeV220/VoidChestAPI/commit/1f07ad7bb63bf4ff92ca751d9f48fff3289c6199))

# [1.18.0-alpha.17](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.16...v1.18.0-alpha.17) (2023-12-09)


### Features

* **VoidInventory:** Added internal open(Player) method ([429b9e1](https://github.com/GeorgeV220/VoidChestAPI/commit/429b9e156d694f575bd3097864473a0f4d423296))

# [1.18.0-alpha.16](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.15...v1.18.0-alpha.16) (2023-12-09)


### Bug Fixes

* **NullableFixedSizeList:** Fixed clear method ([dee3c24](https://github.com/GeorgeV220/VoidChestAPI/commit/dee3c240671681793d90a045d3b78fa5bf8bf681))

# [1.18.0-alpha.15](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.14...v1.18.0-alpha.15) (2023-12-09)


### Features

* Added NullableFixedSizeList(Integer, Collection) constructor to NullableFixedSizeList ([2e8fa38](https://github.com/GeorgeV220/VoidChestAPI/commit/2e8fa384f7cefea810d3dd385f7ec8a4c505c09f))

# [1.18.0-alpha.14](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.13...v1.18.0-alpha.14) (2023-12-09)


### Features

* Added NullablePreFilledArrayList ([6452271](https://github.com/GeorgeV220/VoidChestAPI/commit/64522718366301251f8424488384d4732d4fd04a))

# [1.18.0-alpha.13](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.12...v1.18.0-alpha.13) (2023-12-09)


### Bug Fixes

* **VoidInventory:** modify the update methods ([0ade079](https://github.com/GeorgeV220/VoidChestAPI/commit/0ade0796f3bccfeb10329c3bf08c1ee9cb95e9b6))

# [1.18.0-alpha.12](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.11...v1.18.0-alpha.12) (2023-12-09)


### Features

* **VoidInventory:** Added update methods ([9e08dbe](https://github.com/GeorgeV220/VoidChestAPI/commit/9e08dbe85fe4b5a388853d00824b07663e0b1846))

# [1.18.0-alpha.11](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.10...v1.18.0-alpha.11) (2023-12-08)


### Features

* **IVoidStorage:** Removed whitelistInventoryItemStacks blacklistInventoryItemStacks and blockInventoryItemStacks ([0a25186](https://github.com/GeorgeV220/VoidChestAPI/commit/0a2518686b7399d821926ec2f5d126a9f56ea4a3))

# [1.18.0-alpha.10](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.9...v1.18.0-alpha.10) (2023-12-08)


### Features

* **IVoidStorageManager:** Added voidStorage(SerializableBlock) voidStorage(Location) and voidStorage(SerializableLocation) ([1a62155](https://github.com/GeorgeV220/VoidChestAPI/commit/1a6215524a674da9a43f9b750b1cabba5e4db7f8))
* Removed unused InventoryManager ([03cb843](https://github.com/GeorgeV220/VoidChestAPI/commit/03cb843d65827da10476dd7eabd25403bc91da84))

# [1.18.0-alpha.9](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.8...v1.18.0-alpha.9) (2023-12-06)


### Features

* Added JavaDocs to inventory classes and new methods to VoidInventory ([a415405](https://github.com/GeorgeV220/VoidChestAPI/commit/a415405d57093fa85570b4d41ab17f39963339d2))

# [1.18.0-alpha.8](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.7...v1.18.0-alpha.8) (2023-12-05)


### Features

* Rename ShopManager to IShopManager ([4712ead](https://github.com/GeorgeV220/VoidChestAPI/commit/4712eadda114dd5c8cf10f7a1a12bdc685d886da))

# [1.18.0-alpha.7](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.6...v1.18.0-alpha.7) (2023-12-05)


### Features

* **Inventory:** Refactor Inventory classes, removed SerializableInventory and added VoidInventoryItemStack and InventoryHandler ([c709f4c](https://github.com/GeorgeV220/VoidChestAPI/commit/c709f4ceb536de13c7c63215525952159353bd50))

# [1.18.0-alpha.6](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.5...v1.18.0-alpha.6) (2023-12-02)


### Bug Fixes

* **SerializableItemStack:** NBT (de)serialization ([6d180fd](https://github.com/GeorgeV220/VoidChestAPI/commit/6d180fd1fdd67b3d20435c57ab440538f33dadad))

# [1.18.0-alpha.5](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.4...v1.18.0-alpha.5) (2023-12-01)


### Features

* Added InventoryHandler and changed VoidInventory to interface ([6f6eb50](https://github.com/GeorgeV220/VoidChestAPI/commit/6f6eb50171b69aa490f3b8b3f33d3eed61b71c51))

# [1.18.0-alpha.4](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.3...v1.18.0-alpha.4) (2023-12-01)


### Bug Fixes

* Mark IVoidStorage blockInventoryItemStacks for removal ([be4684b](https://github.com/GeorgeV220/VoidChestAPI/commit/be4684b11850fd3558093fddf2bb27b40b2f4c64))

# [1.18.0-alpha.3](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.2...v1.18.0-alpha.3) (2023-12-01)


### Features

* Added VoidInventory ([050f188](https://github.com/GeorgeV220/VoidChestAPI/commit/050f188f721bc8f50acc2a6fd19030c9d37b5083))

# [1.18.0-alpha.2](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.18.0-alpha.1...v1.18.0-alpha.2) (2023-12-01)


### Features

* **Options:** Added OPTIONS_INFINITE_ITEM_LORE and OPTIONS_SHOP_ITEM_LORE to VoidChestOptionsUtil ([728e03e](https://github.com/GeorgeV220/VoidChestAPI/commit/728e03ef219fedf4b3c68b17f93585e57eeecefa))

# [1.18.0-alpha.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.17.0...v1.18.0-alpha.1) (2023-11-30)


### Features

* **IVoidStorage:** Added blockInventory method() ([bcc2f5f](https://github.com/GeorgeV220/VoidChestAPI/commit/bcc2f5fc7701fdc6589cefd82cb83a705ef2ff7e))

# [1.17.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.16.0...v1.17.0) (2023-11-28)


### Features

* Add ItemSpawnEvent ([b3b17ac](https://github.com/GeorgeV220/VoidChestAPI/commit/b3b17ac9dd54394cb193fe1558c7a1f774a926d7))
* Add MONITOR EventPriority ([0ef0c63](https://github.com/GeorgeV220/VoidChestAPI/commit/0ef0c63f337c6eabb7799ff09fb9446f0fe47924))
* Added VoidStorageConfigurationFileCache to manage the voidstorage CFGs files ([7507b24](https://github.com/GeorgeV220/VoidChestAPI/commit/7507b24a3d3ca6bc6e27c75aa302cc038b302b57))
* Remove SerializableItemStackSpawnEvent ([a468aad](https://github.com/GeorgeV220/VoidChestAPI/commit/a468aad90ccbabd151efd5c5bdc14c8cdcd01cdf)), closes [#29329aff2dc880e3bae10069bc092a27bdf13044](https://github.com/GeorgeV220/VoidChestAPI/issues/29329aff2dc880e3bae10069bc092a27bdf13044)
* Rename SerializableItemStackSpawnEvent to InstantItemSpawnEvent ([29329af](https://github.com/GeorgeV220/VoidChestAPI/commit/29329aff2dc880e3bae10069bc092a27bdf13044))

# [1.17.0-beta.3](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.17.0-beta.2...v1.17.0-beta.3) (2023-11-26)


### Features

* Added VoidStorageConfigurationFileCache to manage the voidstorage CFGs files ([7507b24](https://github.com/GeorgeV220/VoidChestAPI/commit/7507b24a3d3ca6bc6e27c75aa302cc038b302b57))

# [1.17.0-beta.2](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.17.0-beta.1...v1.17.0-beta.2) (2023-11-25)


### Features

* Remove SerializableItemStackSpawnEvent ([a468aad](https://github.com/GeorgeV220/VoidChestAPI/commit/a468aad90ccbabd151efd5c5bdc14c8cdcd01cdf)), closes [#29329aff2dc880e3bae10069bc092a27bdf13044](https://github.com/GeorgeV220/VoidChestAPI/issues/29329aff2dc880e3bae10069bc092a27bdf13044)

# [1.17.0-beta.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.16.0...v1.17.0-beta.1) (2023-11-25)


### Features

* Add ItemSpawnEvent ([b3b17ac](https://github.com/GeorgeV220/VoidChestAPI/commit/b3b17ac9dd54394cb193fe1558c7a1f774a926d7))
* Add MONITOR EventPriority ([0ef0c63](https://github.com/GeorgeV220/VoidChestAPI/commit/0ef0c63f337c6eabb7799ff09fb9446f0fe47924))
* Rename SerializableItemStackSpawnEvent to InstantItemSpawnEvent ([29329af](https://github.com/GeorgeV220/VoidChestAPI/commit/29329aff2dc880e3bae10069bc092a27bdf13044))

# [1.16.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.15.0...v1.16.0) (2023-11-25)


### Features

* Add Location to SerializableItemStackSpawnEvent.java ([dce2fdc](https://github.com/GeorgeV220/VoidChestAPI/commit/dce2fdc891d932dd6f15c86f57dc62f10a806b51))

# [1.15.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.14.0...v1.15.0) (2023-11-21)


### Features

* Add VoidInventoryHolder#inventoryType() method ([886db1c](https://github.com/GeorgeV220/VoidChestAPI/commit/886db1c17b2da88ecee976d7c4974a2345471eac))
* MENU Inventory Type ([7d6bc53](https://github.com/GeorgeV220/VoidChestAPI/commit/7d6bc53db45fc15c05a21cab549460621e149828))

# [1.14.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.13.0...v1.14.0) (2023-11-14)


### Features

* Update SerializableItemStackSpawnEvent ([eaa1a51](https://github.com/GeorgeV220/VoidChestAPI/commit/eaa1a51f84d84cb4e299d8cdeb1156e4063d1533))

# [1.13.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.12.0...v1.13.0) (2023-11-14)


### Features

* Add SerializableItemStackSpawnEvent ([6ccf9be](https://github.com/GeorgeV220/VoidChestAPI/commit/6ccf9beb31063dab55f52f878ac89ea9916306f5))

# [1.12.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.11.0...v1.12.0) (2023-11-13)


### Bug Fixes

* **SerializableItemStack:** Fixed method calls ([4a25196](https://github.com/GeorgeV220/VoidChestAPI/commit/4a25196be5d2eb8b2de0378b6a35d35aa56e27d1))


### Features

* Add inventory methods to IVoidStorageManager ([2ff785f](https://github.com/GeorgeV220/VoidChestAPI/commit/2ff785fbdab4cc951b1803fcb634696f07471e77))
* Add serializeItemStacksToNBT(List<SerializableItemStack>) ([f62d62a](https://github.com/GeorgeV220/VoidChestAPI/commit/f62d62adec9402eb62e3d5d7bd6ee8f9e32d446e))
* Add splitBigIntegerTo method to Utilities ([107d45c](https://github.com/GeorgeV220/VoidChestAPI/commit/107d45c1fd6f40926bb5b6cc7376634fe44f738c))
* Add support for item stack amount in SerializableItemStack ([c39629b](https://github.com/GeorgeV220/VoidChestAPI/commit/c39629b1573792e17fd9b7d082b0c2cbbe179a41))
* **Stats:** Add methods for retrieving and updating items stored count ([5921fe5](https://github.com/GeorgeV220/VoidChestAPI/commit/5921fe5580dcf2d3d7c4f58e5f039d02cd80b13b))

# [1.12.0-alpha.3](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.12.0-alpha.2...v1.12.0-alpha.3) (2023-11-12)


### Features

* **Stats:** Add methods for retrieving and updating items stored count ([5921fe5](https://github.com/GeorgeV220/VoidChestAPI/commit/5921fe5580dcf2d3d7c4f58e5f039d02cd80b13b))

# [1.12.0-alpha.2](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.12.0-alpha.1...v1.12.0-alpha.2) (2023-11-12)


### Features

* Add serializeItemStacksToNBT(List<SerializableItemStack>) ([f62d62a](https://github.com/GeorgeV220/VoidChestAPI/commit/f62d62adec9402eb62e3d5d7bd6ee8f9e32d446e))

# [1.12.0-alpha.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.11.0...v1.12.0-alpha.1) (2023-11-12)


### Bug Fixes

* **SerializableItemStack:** Fixed method calls ([4a25196](https://github.com/GeorgeV220/VoidChestAPI/commit/4a25196be5d2eb8b2de0378b6a35d35aa56e27d1))


### Features

* Add inventory methods to IVoidStorageManager ([2ff785f](https://github.com/GeorgeV220/VoidChestAPI/commit/2ff785fbdab4cc951b1803fcb634696f07471e77))
* Add splitBigIntegerTo method to Utilities ([107d45c](https://github.com/GeorgeV220/VoidChestAPI/commit/107d45c1fd6f40926bb5b6cc7376634fe44f738c))
* Add support for item stack amount in SerializableItemStack ([c39629b](https://github.com/GeorgeV220/VoidChestAPI/commit/c39629b1573792e17fd9b7d082b0c2cbbe179a41))

# [1.12.0-beta.3](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.12.0-beta.2...v1.12.0-beta.3) (2023-11-11)


### Features

* Add splitBigIntegerTo method to Utilities ([107d45c](https://github.com/GeorgeV220/VoidChestAPI/commit/107d45c1fd6f40926bb5b6cc7376634fe44f738c))

# [1.12.0-beta.2](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.12.0-beta.1...v1.12.0-beta.2) (2023-11-11)


### Bug Fixes

* **SerializableItemStack:** Fixed method calls ([4a25196](https://github.com/GeorgeV220/VoidChestAPI/commit/4a25196be5d2eb8b2de0378b6a35d35aa56e27d1))

# [1.12.0-beta.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.11.0...v1.12.0-beta.1) (2023-11-11)


### Features

* Add inventory methods to IVoidStorageManager ([2ff785f](https://github.com/GeorgeV220/VoidChestAPI/commit/2ff785fbdab4cc951b1803fcb634696f07471e77))

# [1.11.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.10.0...v1.11.0) (2023-11-11)


### Features

* Add method to convert ItemStack List to SerializableItemStack List ([0c4f131](https://github.com/GeorgeV220/VoidChestAPI/commit/0c4f1315d9a92324a4551b4e2277cbe38381a2f2))

# [1.10.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.9.0...v1.10.0) (2023-11-11)


### Features

* Add methods to serialize and deserialize lists of ItemStacks ([74c6526](https://github.com/GeorgeV220/VoidChestAPI/commit/74c6526e9458035e0e47ba7f828a0d227b4f816d))

# [1.9.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.8.0...v1.9.0) (2023-11-11)


### Features

* Add methods for serializing and deserializing ItemStacks- Added `serializeItemStack` method to serialize an ItemStack into a base32-encoded string.- Added `deserializeItemStack` method to deserialize a base32-encoded string into an ItemStack ([b48ff56](https://github.com/GeorgeV220/VoidChestAPI/commit/b48ff5649bf98f27bbdab231634e49d1662ae37f))

# [1.8.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.7.0...v1.8.0) (2023-11-11)


### Features

* Add SerializableItemStack class ([1124f97](https://github.com/GeorgeV220/VoidChestAPI/commit/1124f979f96c26dfd220ee01d9994bfab445c708))
* Add SerializerException class ([9fdce21](https://github.com/GeorgeV220/VoidChestAPI/commit/9fdce21d6d3ee362619030c4e48a811afcbdc249))
* Refactor IVoidStorage interface to use List of SerializableItemStacks for block inventory, whitelist inventory, and blacklist inventory ([6f63445](https://github.com/GeorgeV220/VoidChestAPI/commit/6f63445e4673cb63ebc8b3aa8b10cf9eb26f5f0d))

# [1.7.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.6.1...v1.7.0) (2023-11-10)


### Features

* Add ShopManager Shop and ShopItem classes ([e7d8211](https://github.com/GeorgeV220/VoidChestAPI/commit/e7d8211dd40062163d99c06de0f712d856051e1a))

## [1.6.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.6.0...v1.6.1) (2023-10-29)


### Bug Fixes

* Change SerializableInventory to return InventoryHolder inventory instead of a new one ([6d8e7aa](https://github.com/GeorgeV220/VoidChestAPI/commit/6d8e7aab8c5347f76afdf5af1ec16f8ca88171f5))

# [1.6.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.5.0...v1.6.0) (2023-10-28)


### Features

* Add PlayerDataLoadEvent, PlayerDataSaveEvent, VoidStorageLoadEvent, and VoidStorageSaveEvent ([26e6ced](https://github.com/GeorgeV220/VoidChestAPI/commit/26e6ced4045bbb86a5ac501b1194656b1d3c4ceb))
* **event:** add PlayerEvent class ([ab9178f](https://github.com/GeorgeV220/VoidChestAPI/commit/ab9178fdee1e2ff80e0eeb1e492fea3b5fdd5084))

# [1.5.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.4.2...v1.5.0) (2023-10-27)


### Features

* Add reload methods to player data and void storage ([e9a576c](https://github.com/GeorgeV220/VoidChestAPI/commit/e9a576c08b6c96adf28872d4d147fa1056e2e1ca))

## [1.4.2](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.4.1...v1.4.2) (2023-10-10)


### Bug Fixes

* **publish.gradle:** Update artifactId and groupId in shadow publication ([f44aaf2](https://github.com/GeorgeV220/VoidChestAPI/commit/f44aaf2974c10a2eb2bd8a7d3e0181da9f913356))
* **readme:** Update VoidChestAPI dependency in README.md ([774a6df](https://github.com/GeorgeV220/VoidChestAPI/commit/774a6dfab1b879b2dd743559e0ee67bf8ba9667f))

## [1.4.1](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.4.0...v1.4.1) (2023-10-10)


### Bug Fixes

* **gradle:** Fix URLs in gradle publishing configuration ([fedb8d0](https://github.com/GeorgeV220/VoidChestAPI/commit/fedb8d0029b6b66b5d01f4d1dac6524d14791c35))

# [1.4.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.3.0...v1.4.0) (2023-10-10)


### Features

* Add classifier for the shaded jar and update README ([30c319b](https://github.com/GeorgeV220/VoidChestAPI/commit/30c319b9e889de63291bbe5c00659114a37a29f5))

# [1.3.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.2.0...v1.3.0) (2023-10-06)


### Features

* **stats:** Add methods for retrieving, setting, and adding total money, items sold, and items purged stored ([f175786](https://github.com/GeorgeV220/VoidChestAPI/commit/f175786205c8c637ed0faa37a2bcb6e6e2ca8ba9))

# [1.2.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.1.0...v1.2.0) (2023-09-23)


### Features

* Change whitelist and blacklist parameters and return types from string to itemstack ([43d7ca9](https://github.com/GeorgeV220/VoidChestAPI/commit/43d7ca994efa3700779961b1ade0eac517c53858))

# [1.1.0](https://github.com/GeorgeV220/VoidChestAPI/compare/v1.0.0...v1.1.0) (2023-09-18)


### Features

* **IVoidStorage:** whitelist and blacklist inventory ([3201df1](https://github.com/GeorgeV220/VoidChestAPI/commit/3201df1d285f90deb0259be6a9fa598d5579cd82))

# 1.0.0 (2023-09-16)


### Features

* **api:** VoidChestAPI initial commit ([283e714](https://github.com/GeorgeV220/VoidChestAPI/commit/283e7149713590b481811a7e58312e57ddda692c))
