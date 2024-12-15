/*
 * Copyright (c) 2024 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.smoothcloud.chain.network;

public enum PacketId {

    // Allgemeine Cloud-Kommunikation
    HEARTBEAT_PACKET(100),
    CLOUD_STATUS_PACKET(101),
    CLOUD_PING_PACKET(102),
    SHUTDOWN_CLOUD_PACKET(103),
    ERROR_REPORT_PACKET(104),

    // Server-Management
    START_SERVER_PACKET(200),
    STOP_SERVER_PACKET(201),
    RESTART_SERVER_PACKET(202),
    DEPLOY_SERVER_PACKET(203),
    UPDATE_SERVER_CONFIG_PACKET(204),
    SERVER_STATUS_REQUEST_PACKET(205),
    SERVER_STATUS_RESPONSE_PACKET(206),
    ALLOCATE_SERVER_PACKET(207),
    DELETE_SERVER_PACKET(208),

    // Spieler-Management
    PLAYER_LOGIN_PACKET(300),
    PLAYER_LOGOUT_PACKET(301),
    PLAYER_TRANSFER_PACKET(302),
    BAN_PLAYER_PACKET(303),
    UNBAN_PLAYER_PACKET(304),
    PLAYER_DATA_REQUEST_PACKET(305),
    PLAYER_DATA_RESPONSE_PACKET(306),
    KICK_PLAYER_PACKET(307),

    // Kommunikation (Chat, Messaging)
    CHAT_MESSAGE_PACKET(400),
    BROADCAST_MESSAGE_PACKET(401),
    PRIVATE_MESSAGE_PACKET(402),
    SERVER_ANNOUNCEMENT_PACKET(403),

    // Netzwerk-Management
    REGISTER_SERVER_PACKET(501),
    UNREGISTER_SERVER_PACKET(502),
    NETWORK_TOPOLOGY_PACKET(503),
    RECONNECT_PACKET(504),
    CHANGE_REGION_PACKET(505),

    // Monitoring und Analyse
    RESOURCE_USAGE_PACKET(600),
    PERFORMANCE_METRICS_PACKET(601),
    LOG_FILE_PACKET(602),
    CRASH_REPORT_PACKET(603),
    ANALYTICS_PACKET(604),

    // Sicherheit
    AUTH_REQUEST_PACKET(700),
    AUTH_RESPONSE_PACKET(701),
    PERMISSION_UPDATE_PACKET(702),
    SESSION_VALIDATION_PACKET(703),
    SECURITY_ALERT_PACKET(704),

    // Dateiverwaltung
    UPLOAD_FILE_PACKET(800),
    DOWNLOAD_FILE_PACKET(801),
    DELETE_FILE_PACKET(802),
    SYNC_FILE_PACKET(803),

    // Events und Custom-Pakete
    CUSTOM_EVENT_PACKET(900),
    SERVER_EVENT_PACKET(901),
    CLOUD_EVENT_PACKET(902),

    // Skalierung und Deployment
    SCALE_UP_PACKET(1000),
    SCALE_DOWN_PACKET(1100),
    CREATE_CLUSTER_PACKET(1200),
    MERGE_CLUSTER_PACKET(1300),
    DEPLOY_UPDATE_PACKET(1400),

    // API und Interaktion
    COMMAND_EXECUTION_PACKET(1110),
    COMMAND_RESULT_PACKET(1120),

    // Debugging und Entwicklung
    DEBUG_INFO_PACKET(1210),
    TEST_PACKET(1220),
    DEV_COMMAND_PACKET(1230),

    // Wartung
    MAINTENANCE_START_PACKET(1310),
    MAINTENANCE_END_PACKET(1320),
    BACKUP_REQUEST_PACKET(1330),
    BACKUP_RESPONSE_PACKET(1340),
    RESTORE_BACKUP_PACKET(1350);

    private final int id;

    PacketId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PacketId fromId(int id) {
        for (PacketId packet : values()) {
            if (packet.id == id) {
                return packet;
            }
        }
        throw new IllegalArgumentException("Invalid Packet ID: " + id);
    }
}

