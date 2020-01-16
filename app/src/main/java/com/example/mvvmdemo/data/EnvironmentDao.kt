package com.example.mvvmdemo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface EnvironmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(environmentInfo: EnvironmentInfo)

    @Query("SELECT * FROM environment_info WHERE id = 0 LIMIT 1")
    suspend fun load(): LiveData<EnvironmentInfo>

    @Query("UPDATE environment_info SET token = :token WHERE id = 0")
    fun updateToken(token: String)

    @Query(
        """
        UPDATE environment_info
        SET
        user_guid = :userGuid,
        user_name = :userName,
        user_account = :userAccount,
        user_tel = :userTel
        WHERE id = 0
    """
    )
    fun updateUser(userGuid: String, userName: String, userAccount: String, userTel: String)

    @Query(
        """
        UPDATE environment_info
        SET
        device_id = :deviceId,
        device_type = :deviceType,
        enterprise_guid = :enterpriseGuid,
        enterprise_name = :enterpriseName,
        valid_start_time = :validStartTime,
        valid_end_time = :validEndTime,
        store_guid = :storeGuid,
        store_name = :storeName,
        user_guid = :userGuid,
        user_name = :userName,
        user_account = :userAccount,
        user_tel = :userTel,
        storeNumber = :storeNo
        WHERE id = 0
    """
    )
    fun updateAllExceptToken(
        deviceId: String, deviceType: Int,
        enterpriseGuid: String, enterpriseName: String, validStartTime: Date, validEndTime: Date,
        storeGuid: String, storeName: String,
        userGuid: String, userName: String, userAccount: String, userTel: String, storeNo: String
    )
}