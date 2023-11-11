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
